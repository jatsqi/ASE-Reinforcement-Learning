package de.jquast.plugin.repository;

import de.jquast.application.service.AgentService;
import de.jquast.application.service.EnvironmentService;
import de.jquast.domain.exception.PersistStoreException;
import de.jquast.domain.shared.ActionValueRepository;
import de.jquast.domain.shared.ActionValueStore;
import de.jquast.domain.shared.PersistedStoreInfo;
import de.jquast.utils.di.annotations.Inject;
import de.jquast.utils.files.CSVReader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Stream;

public class FileSystemActionValueRepository implements ActionValueRepository {

    private static final String STORAGE_FOLDER_PATH = "stored_values";
    private static final String STORAGE_FILE_EXTENSION = "store";
    private static final String STORAGE_FILE_DELIMITER = ";";

    private Map<Integer, PersistedStoreInfo> valueInfo = new HashMap();

    private AgentService agentService;
    private EnvironmentService environmentService;

    @Inject
    public FileSystemActionValueRepository(AgentService agentService, EnvironmentService environmentService) {
        this.agentService = agentService;
        this.environmentService = environmentService;

        try {
            if (!Files.exists(Path.of(STORAGE_FOLDER_PATH))) {
                Files.createDirectory(Path.of(STORAGE_FOLDER_PATH));
            }
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Exception Handling
        }
    }

    private static String infoToFileName(PersistedStoreInfo info) {
        return infoToFileName(info.id(), info.agent(), info.environment());
    }

    private static String infoToFileName(int id, String agent, String env) {
        return String.format("%d_%s_%s.%s",
                id,
                agent,
                env,
                STORAGE_FILE_EXTENSION);
    }

    private static Optional<PersistedStoreInfo> fileNameToInfo(String fileName) {
        String[] parts = fileName.split("\\.")[0].split("_");
        if (parts.length != 3)
            return Optional.empty();

        return Optional.of(new PersistedStoreInfo(Integer.parseInt(parts[0]), parts[1], parts[2]));
    }

    @Override
    public Collection<PersistedStoreInfo> getStoredActionValueInfo() {
        updateValueInfo();
        return valueInfo.values();
    }

    @Override
    public Optional<PersistedStoreInfo> getInfoById(int id) {
        updateValueInfo();
        return Optional.ofNullable(valueInfo.get(id));
    }

    @Override
    public Optional<ActionValueStore> fetchStoreFromInfo(PersistedStoreInfo info) {
        return readActionValueStore(info);
    }

    @Override
    public PersistedStoreInfo persistActionValueStore(String agentName, String envName, ActionValueStore store) throws PersistStoreException {
        int id = 0;
        try {
            id = findNextId(agentName, envName);
        } catch (IOException e) {
            throw new PersistStoreException("Es gab einen Fehler beim Lesen des Ordners der Value Stores!");
        }
        PersistedStoreInfo info = new PersistedStoreInfo(id, agentName, envName);

        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(STORAGE_FOLDER_PATH, infoToFileName(info)), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write("state;action;value\n");

            for (int i = 0; i < store.getStateCount(); i++) {
                for (int j = 0; j < store.getActionCount(); j++) {
                    writer.write(String.format(Locale.US, "%d;%d;%f\n", i, j, store.getActionValue(i, j)));
                }
            }
        } catch (IOException e) {
            throw new PersistStoreException("Es gab einen Fehler beim Öffnen der Datei zum Schreiben des Stores!");
        }

        return info;
    }

    private void updateValueInfo() {
        try (Stream<Path> foundFiles = Files.walk(Path.of(STORAGE_FOLDER_PATH))) {
            List<Path> storeFiles = foundFiles
                    .filter(p -> p.toString().endsWith(STORAGE_FILE_EXTENSION)).toList();

            valueInfo.clear();

            for (Path p : storeFiles) {
                Optional<PersistedStoreInfo> info = fileNameToInfo(p.getFileName().toString());

                if (info.isPresent())
                    valueInfo.put(info.get().id(), info.get());
            }
        } catch (IOException e) {
            // Fehler vorerst ignorieren, da in diesem Fall ein Problem mit dem Ordner selber besteht
        }
    }

    private Optional<ActionValueStore> readActionValueStore(PersistedStoreInfo info) {
        /*
        CSV Aufbau:
            state;action;value
            (double);(double);(double)
            ....
         */
        CSVReader reader = new CSVReader(Path.of(STORAGE_FOLDER_PATH, infoToFileName(info)), STORAGE_FILE_DELIMITER);
        Optional<String[][]> varsOp = reader.read();

        if (varsOp.isEmpty())
            return Optional.empty();

        String[][] vars = varsOp.get();

        // Letzte Zeile enthält Nummer des maximalen States und der maximalen Action
        StoreCSVEntry lastEntry = parseLine(vars[vars.length - 1]);
        double[][] values = new double[lastEntry.state + 1][lastEntry.action + 1];

        // Read every line
        for (String[] line : vars) {
            StoreCSVEntry entry = parseLine(line);

            if (entry.action > lastEntry.action || entry.action < 0 || entry.state > lastEntry.state || entry.state < 0)
                return Optional.empty();

            values[entry.state][entry.action] = entry.value;
        }

        // Create store
        ActionValueStore store = new ActionValueStore(values);
        return Optional.of(store);
    }

    private StoreCSVEntry parseLine(String[] cols) {
        return new StoreCSVEntry(
                Integer.parseInt(cols[0]),
                Integer.parseInt(cols[1]),
                Double.parseDouble(cols[2])
        );
    }

    private int findNextId(String agentName, String envName) throws IOException {
        int currentMaxId = 0;

        try (Stream<Path> foundFiles = Files.walk(Path.of(STORAGE_FOLDER_PATH))) {
            List<Path> storeFiles = foundFiles
                    .filter(p -> p.toString().endsWith(STORAGE_FILE_EXTENSION)).toList();

            for (Path f : storeFiles) {
                String[] parts = f.getFileName().toString().split("_");

                int id;
                try {
                    id = Integer.parseInt(parts[0]);
                } catch (NumberFormatException ex) {
                    continue;
                }

                if (currentMaxId < id)
                    currentMaxId = id;
            }
        }

        return currentMaxId + 1;
    }

    private record StoreCSVEntry(int state, int action, double value) {
    }

}
