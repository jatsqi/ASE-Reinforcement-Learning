package de.jquast.plugin.factory;

import de.jquast.domain.environment.Environment;
import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.domain.environment.EnvironmentFactory;
import de.jquast.plugin.environments.GridWorldEnvironment;
import de.jquast.plugin.environments.KArmedBanditEnvironment;

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

        return Optional.of(environment);
    }

    private KArmedBanditEnvironment createKArmedBanditEnvironment(Map<String, String> parameters) {
        Integer k = Integer.parseInt(parameters.get("bandits"));

        return new KArmedBanditEnvironment(k);
    }

    private GridWorldEnvironment createGridWorldEnvironment(Map<String, String> parameters) {
        Integer height = Integer.parseInt(parameters.get("height"));
        Integer width = Integer.parseInt(parameters.get("width"));

        return new GridWorldEnvironment(height, width);
    }
}
