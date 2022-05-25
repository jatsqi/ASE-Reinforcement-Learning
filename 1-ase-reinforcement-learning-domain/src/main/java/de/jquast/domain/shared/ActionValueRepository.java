package de.jquast.domain.shared;

import de.jquast.domain.exception.PersistStoreException;

import java.util.Collection;
import java.util.Optional;

public interface ActionValueRepository {

    Collection<PersistedStoreInfo> getStoredActionValueInfo();

    Optional<PersistedStoreInfo> getInfoById(int id);

    Optional<ActionValueStore> fetchStoreFromInfo(PersistedStoreInfo info);

    PersistedStoreInfo persistActionValueStore(String environment, String agent, ActionValueStore store) throws PersistStoreException;

}
