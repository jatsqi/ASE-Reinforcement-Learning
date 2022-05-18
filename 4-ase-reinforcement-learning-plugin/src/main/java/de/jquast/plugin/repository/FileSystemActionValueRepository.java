package de.jquast.plugin.repository;

import de.jquast.application.service.AgentService;
import de.jquast.application.service.EnvironmentService;
import de.jquast.domain.agent.AgentDescriptor;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.domain.shared.ActionValueRepository;
import de.jquast.domain.shared.ActionValueStore;
import de.jquast.domain.shared.StoredValueInfo;
import de.jquast.utils.di.annotations.Inject;
import de.jquast.utils.files.CSVReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FileSystemActionValueRepository implements ActionValueRepository {

    private static final String STORAGE_FOLDER_PATH = "stored_values";
    private static final String STORAGE_FILE_EXTENSION = "csv";
    private static final String STORAGE_FILE_DELIMITER = ";";

    private Map<Integer, StoredValueInfo> valueInfo = new HashMap();

    private AgentService agentService;
    private EnvironmentService environmentService;

    @Inject
    public FileSystemActionValueRepository(AgentService agentService, EnvironmentService environmentService) {
        this.agentService = agentService;
        this.environmentService = environmentService;
    }

    @Override
    public Collection<StoredValueInfo> getStoredActionValueInfo() {
        updateValueInfo();
        return valueInfo.values();
    }

    @Override
    public Optional<StoredValueInfo> getStoredActionValueInfoById(int id) {
        updateValueInfo();
        return Optional.ofNullable(valueInfo.get(id));
    }

    @Override
    public Optional<StoredValueInfo> getStoredActionValueInfoByName(String name) {
        updateValueInfo();
        return valueInfo.values().stream().filter(info -> info.name().equals(name)).findFirst();
    }

    @Override
    public Optional<ActionValueStore> fetchActionValueInfo(StoredValueInfo info) {
        return readActionValueStore(info);
    }

    @Override
    public StoredValueInfo createActionValueStore(ActionValueStore store) {
        return null;
    }

    private void updateValueInfo() {
        try {
            List<Path> csvFiles = Files.walk(Paths.get(STORAGE_FOLDER_PATH))
                    .filter(p -> p.toString().endsWith(STORAGE_FILE_EXTENSION))
                    .collect(Collectors.toList());
            valueInfo.clear();

            for (Path p : csvFiles) {
                Optional<StoredValueInfo> info = fileNameToInfo(p.getFileName().toString());

                if (info.isPresent())
                    valueInfo.put(info.get().id(), info.get());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Optional<StoredValueInfo> fileNameToInfo(String fileName) {
        String[] parts = fileName.split("_");
        if (parts.length != 4)
            return Optional.empty();

        return Optional.of(new StoredValueInfo(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]));
    }

    private static String infoToFileName(StoredValueInfo info) {
        return String.format("%d_%s_%s_%s",
                info.id(),
                info.name().replace("_", ""),
                info.agent().replace("_", ""),
                info.environment().replace("_", ""));
    }

    private Optional<ActionValueStore> readActionValueStore(StoredValueInfo info) {
        /*
        CSV Aufbau:
            state;action;value
            (double);(double);(double)
            ....
         */
        CSVReader reader = new CSVReader(Path.of(STORAGE_FOLDER_PATH, infoToFileName(info)), STORAGE_FILE_DELIMITER);
        Optional<String[][]> vars = reader.read();

        if (vars.isEmpty())
            return Optional.empty();

        Optional<EnvironmentDescriptor> environment = environmentService.getEnvironment(info.environment());
        Optional<AgentDescriptor> agent = agentService.getAgent(info.agent());

        if (environment.isEmpty() || agent.isEmpty())
            return Optional.empty();

        AgentDescriptor agentDescriptor = agent.get();
        EnvironmentDescriptor environmentDescriptor = environment.get();

        //ActionValueStore store = new ActionValueStore(environmentDescriptor.)
        return Optional.empty();
    }
}
