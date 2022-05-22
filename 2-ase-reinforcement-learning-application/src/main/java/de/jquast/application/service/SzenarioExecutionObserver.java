package de.jquast.application.service;

import de.jquast.application.session.SzenarioProgressObserver;
import de.jquast.domain.shared.PersistedStoreInfo;

public interface SzenarioExecutionObserver extends SzenarioProgressObserver {
    default void onActionStorePersisted(PersistedStoreInfo info) {
    }
}
