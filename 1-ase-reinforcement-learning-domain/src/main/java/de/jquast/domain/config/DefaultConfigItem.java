package de.jquast.domain.config;

public enum DefaultConfigItem {
    MODEL_OUTPUT_FORMAT("DEFAULT_MODEL_OUTPUT_FORMAT", new String[]{"csv", "txt"}, "csv"),
    MODEL_OUTPUT_DIRECTORY("DEFAULT_MODEL_OUTPUT_DIRECTORY", null, "."),
    ALGORITHM_TIME_STEPS("DEFAULT_ALGORITHM_TIME_STEPS", null, "100"),
    ALGORITHM_EXPLORATION_RATE("DEFAULT_ALGORITHM_EXPLORATION_RATE", null, "0.7"),
    ALGORITHM_DISCOUNT_FACTOR("DEFAULT_ALGORITHM_DISCOUNT", null, "0.95"),
    ALGORITHM_LEARNING_RARE("DEFAULT_ALGORITHM_LEARNING_RATE", null, "0.2"),
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
