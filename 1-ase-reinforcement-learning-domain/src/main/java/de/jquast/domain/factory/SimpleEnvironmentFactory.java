package de.jquast.domain.factory;

import de.jquast.domain.environment.Environment;
import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.domain.environment.EnvironmentFactory;
import de.jquast.domain.environment.impl.GridWorldEnvironment;
import de.jquast.domain.environment.impl.KArmedBanditEnvironment;
import de.jquast.domain.exception.EnvironmentCreationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SimpleEnvironmentFactory implements EnvironmentFactory {

    private static Map<String, EnvironmentConstructor> ENV_CONSTRUCTORS;

    static {
        ENV_CONSTRUCTORS = new HashMap<>();

        ENV_CONSTRUCTORS.put("k-armed-bandit", (descriptor, parameters) -> createKArmedBanditEnvironment(parameters));
        ENV_CONSTRUCTORS.put("grid-world", (descriptor, parameters) -> createGridWorldEnvironment(parameters));
    }

    @Override
    public Optional<Environment> createEnvironment(EnvironmentDescriptor descriptor, Map<String, String> parameters) throws EnvironmentCreationException {
        Environment environment = null;
        if (ENV_CONSTRUCTORS.containsKey(descriptor.name()))
            environment = ENV_CONSTRUCTORS.get(descriptor.name()).constructEnvironment(descriptor, parameters);

        return Optional.ofNullable(environment);
    }

    private static KArmedBanditEnvironment createKArmedBanditEnvironment(Map<String, String> parameters) throws EnvironmentCreationException {
        try {
            Integer k = Integer.parseInt(parameters.get("bandits"));
            return new KArmedBanditEnvironment(k);
        } catch (NumberFormatException e) {
            throw new EnvironmentCreationException("Die Anzahl der Bandits konnte nicht gelesen werden!", "k-armed-bandit");
        }
    }

    private static GridWorldEnvironment createGridWorldEnvironment(Map<String, String> parameters) throws EnvironmentCreationException {
        if (!parameters.containsKey("from")) {
            if (!parameters.containsKey("height") || !parameters.containsKey("width"))
                throw new EnvironmentCreationException("Höhe bzw. Breite oder ein Dateiname sind für die Erstellung erforderlich!", "grid-world");

            try {
                Integer height = Integer.parseInt(parameters.get("height"));
                Integer width = Integer.parseInt(parameters.get("width"));

                return new GridWorldEnvironment(height, width);
            } catch (Exception e) {
                throw new EnvironmentCreationException("Höhe oder Breite konnten nicht gelesen werden!", "grid-world");
            }
        }

        String from = parameters.get("from");
        Path fromPath = Paths.get(from);
        try {
            return new GridWorldEnvironment(parseGridWorldFile(fromPath));
        } catch (IOException e) {
            throw new EnvironmentCreationException(
                    String.format("Die Datei '%s' konnte nicht korrekt gelesen werden!", from), "grid-world");
        }
    }

    private static int[][] parseGridWorldFile(Path fromPath) throws IOException, EnvironmentCreationException {
        List<String> lines = Files.readAllLines(fromPath);
        if (lines.size() <= 0)
            throw new EnvironmentCreationException("Die angegebene Datei ist leer.", "grid-world");

        int[][] grid = new int[lines.get(0).length()][lines.size()];

        for (int i = 0; i < lines.size(); i++) {
            char[] chars = lines.get(i).toCharArray();

            for (int j = 0; j < chars.length; j++) {
                int num = chars[j] - '0';
                grid[j][i] = num;
            }
        }

        return grid;
    }

    private interface EnvironmentConstructor {
        Environment constructEnvironment(EnvironmentDescriptor descriptor, Map<String, String> parameters) throws EnvironmentCreationException;
    }
}
