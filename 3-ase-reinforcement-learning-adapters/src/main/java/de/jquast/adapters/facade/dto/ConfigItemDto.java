package de.jquast.adapters.facade.dto;

public record ConfigItemDto(String key, String value) {
    @Override
    public String toString() {
        return String.format("%-50s: %s ", key, value);
    }
}
