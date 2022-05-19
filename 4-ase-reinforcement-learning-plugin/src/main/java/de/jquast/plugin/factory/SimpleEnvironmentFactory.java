package de.jquast.plugin.factory;

import de.jquast.domain.environment.Environment;
import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.domain.environment.EnvironmentFactory;
import de.jquast.plugin.environments.GridWorldEnvironment;
import de.jquast.plugin.environments.KArmedBanditEnvironment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SimpleEnvironmentFactory implements EnvironmentFactory {

    @Override
    public Optional<Environment> createEnvironment(EnvironmentDescriptor descriptor, Map<String, String> parameters) {
        Environment environment = switch (descriptor.name().toLowerCase()) {
            case "k-armed-bandit" -> createKArmedBanditEnvironment(parameters);
            case "grid-world" -> createGridWorldEnvironment(parameters);
            default -> null;
        };

        return Optional.ofNullable(environment);
    }

    private KArmedBanditEnvironment createKArmedBanditEnvironment(Map<String, String> parameters) {
        Integer k = Integer.parseInt(parameters.get("bandits"));

        return new KArmedBanditEnvironment(k);
    }

    private GridWorldEnvironment createGridWorldEnvironment(Map<String, String> parameters) {
        Integer height = Integer.parseInt(parameters.get("height"));
        Integer width = Integer.parseInt(parameters.get("width"));

        if (!parameters.containsKey("from")) {
            return new GridWorldEnvironment(height, width);
        }

        // Das Parsen könnte man noch in eine eigene Klasse auslagern und über Interface in dieses Objekt injecten,
        // aber für dieses einfache Beispiel ist es denke ich so O.K.

        String from = parameters.get("from");
        Path fromPath = Paths.get(from);
        try {
            List<String> lines = Files.readAllLines(fromPath);
            int[][] grid = new int[lines.get(0).length()][lines.size()];

            for (int i = 0; i < lines.size(); i++) {
                char[] chars = lines.get(i).toCharArray();

                for (int j = 0; j < chars.length; j++) {
                    int num = chars[j] - '0';
                    grid[j][i] = num;
                }
            }

            return new GridWorldEnvironment(grid);
        } catch (IOException e) {
            return null;
        }
    }
}
