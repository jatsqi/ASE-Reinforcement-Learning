package de.jquast.domain.shared;

import java.util.Collection;
import java.util.Optional;

public interface ActionValueRepository {

    Collection<StoredValueInfo> getStoredActionValueInfo();

    Optional<StoredValueInfo> getInfoById(int id);

    Optional<ActionValueStore> fetchStoreFromInfo(StoredValueInfo info);

    StoredValueInfo persistActionValueStore(String environment, String agent, ActionValueStore store);

}
