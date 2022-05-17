package de.jquast.domain.algorithm;

public record RLAlgorithmDescriptor(String name, String description, Class<? extends RLAlgorithm> clazz) {}
