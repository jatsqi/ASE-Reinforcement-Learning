package de.jquast.domain.policy;

import java.util.Objects;

public record PolicyDescriptor(String name, String description) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PolicyDescriptor that = (PolicyDescriptor) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
