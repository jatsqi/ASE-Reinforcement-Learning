package de.jquast.domain.config;

public enum ConfigItems {
    MODEL_OUTPUT_FORMAT("MODEL_OUTPUT_FORMAT", new String[]{ "csv", "txt" }, "csv"),
    MODEL_OUTPUT_DIRECTORY("MODEL_OUTPUT_DIRECTORY", null, ".");

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
