package de.jquast.adapters.facade.dto;

public record RLAlgorithmDescriptorDto(String name, String description) {

    @Override
    public String toString() {
        return String.format("%s: %s", name, description);
    }

}
