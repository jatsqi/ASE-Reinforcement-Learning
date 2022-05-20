package config;

import de.jquast.domain.config.ConfigItem;

import java.util.Optional;

public enum DefaultConfigItem {
    MODEL_OUTPUT_FORMAT("DEFAULT_MODEL_OUTPUT_FORMAT", new String[]{"csv", "txt"}, "csv"),
    MODEL_OUTPUT_DIRECTORY("DEFAULT_MODEL_OUTPUT_DIRECTORY", null, "."),
    ALGORITHM_TIME_STEPS("DEFAULT_ALGORITHM_TIME_STEPS", null, "100"),
    ALGORITHM_EXPLORATION_RATE("DEFAULT_ALGORITHM_EXPLORATION_RATE", null, "0.9"),
    ALGORITHM_DISCOUNT_FACTOR("DEFAULT_ALGORITHM_DISCOUNT", null, "0.95"),
    ALGORITHM_LEARNING_RATE("DEFAULT_ALGORITHM_LEARNING_RATE", null, "0.9"),
    AGENT_REWARD_UPDATE_STEP_SIZE("DEFAULT_AGENT_REWARD_STEP_SIZE", null, "0.1"),
    MESSAGE_TRAINING_AVERAGE_REWARD_STEPS("DEFAULT_MESSAGE_TRAINING_AVERAGE_REWARD_STEPS", null, "20000");

    private String key;
    private String[] predefinedOptions;
    private String defaultValue;

    DefaultConfigItem(String key, String[] predefinedOptions, String defaultValue) {
        this.key = key.toUpperCase();
        this.predefinedOptions = predefinedOptions;
        this.defaultValue = defaultValue;
    }

    public static Optional<DefaultConfigItem> findItem(String key) {
        for (DefaultConfigItem item : values()) {
            if (item.getKey().equalsIgnoreCase(key))
                return Optional.of(item);
        }

        return Optional.empty();
    }

    public String getKey() {
        return key;
    }

    public String[] getPredefinedOptions() {
        return predefinedOptions;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public ConfigItem createNewItem() {
        return new ConfigItem(getKey(), getDefaultValue());
    }

}
