package de.jquast.domain.shared;

import java.util.Objects;

public record PersistedStoreInfo(int id, String agent, String environment) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersistedStoreInfo that = (PersistedStoreInfo) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
