package de.jquast.domain.config;

public record ConfigItem(String name, String value) {

    @Override
    public String toString() {
        return name + " = '" + value + "'";
    }
}
