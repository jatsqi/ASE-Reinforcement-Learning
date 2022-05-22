package de.jquast.adapters.facade;

import de.jquast.application.exception.StartSzenarioException;
import de.jquast.application.service.SzenarioExecutionObserver;

import java.util.Optional;

public interface ExecutionServiceFacade {

    void startTraining(
            String agentName,
            String envName,
            String envOptions,
            long steps,
            int storeId,
            Optional<SzenarioExecutionObserver> observer) throws StartSzenarioException;

    void startEvaluation(
            String agentName,
            String envName,
            String envOptions,
            long steps,
            int storeId,
            Optional<SzenarioExecutionObserver> observer) throws StartSzenarioException;

}
