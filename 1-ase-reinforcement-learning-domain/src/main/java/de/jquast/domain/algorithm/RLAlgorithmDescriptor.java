package de.jquast.domain.algorithm;

public record RLAlgorithmDescriptor(String name, String description, Class<? extends RLAlgorithm> clazz) {

    @Override
    public String toString() {
        return String.format("%s: %s", name, description);
    }
}
