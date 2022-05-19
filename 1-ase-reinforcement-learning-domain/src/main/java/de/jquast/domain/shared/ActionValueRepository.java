package de.jquast.domain.shared;

import java.util.Collection;
import java.util.Optional;

public interface ActionValueRepository {

    Collection<StoredValueInfo> getStoredActionValueInfo();

    Optional<StoredValueInfo> getStoredActionValueInfoById(int id);

    Optional<ActionValueStore> fetchActionValueInfo(StoredValueInfo info);

    StoredValueInfo createActionValueStore(String environment, String agent, ActionValueStore store);

}
