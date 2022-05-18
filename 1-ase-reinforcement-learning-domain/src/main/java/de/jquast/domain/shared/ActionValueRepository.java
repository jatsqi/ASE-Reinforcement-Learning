package de.jquast.domain.shared;

import java.util.Collection;
import java.util.Optional;

public interface ActionValueRepository {

    Collection<StoredValueInfo> getStoredActionValueInfo();

    Optional<StoredValueInfo> getStoredActionValueInfoById(int id);

    Optional<StoredValueInfo> getStoredActionValueInfoByName(String name);

    Optional<ActionValueStore> fetchActionValueInfo(StoredValueInfo info);

    StoredValueInfo createActionValueStore(ActionValueStore store);

}
