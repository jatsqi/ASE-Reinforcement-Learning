package de.jquast.domain.shared;

import java.util.Objects;

public record StoredValueInfo(int id, String agent, String environment) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoredValueInfo that = (StoredValueInfo) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
