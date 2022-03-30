package de.jquast.domain.config;

public enum ConfigItems {
    MODEL_OUTPUT_FORMAT("DEFAULT_MODEL_OUTPUT_FORMAT", new String[]{ "csv", "txt" }, "csv"),
    MODEL_OUTPUT_DIRECTORY("DEFAULT_MODEL_OUTPUT_DIRECTORY", null, "."),
    ALGORITHM_TIME_STEPS("DEFAULT_ALGORITHM_TIME_STEPS", null, "100"),
    ALGORITHM_EXPLORATION_RATE("DEFAULT_ALGORITHM_EXPLORATION_RATE", null, "0.01"),
    ALGORITHM_DISCOUNT_FACTOR("DEFAULT_ALGORITHM_DISCOUNT", null, "0.05");

    private String key;
    private String[] predefinedOptions;
    private String defaultValue;

    ConfigItems(String key, String[] predefinedOptions, String defaultValue) {
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
}
