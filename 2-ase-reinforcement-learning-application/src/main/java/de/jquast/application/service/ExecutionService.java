package de.jquast.application.service;

import de.jquast.application.exception.StartAgentTrainingException;

import java.util.Optional;

public interface ExecutionService {

    void startTraining(
            String agentName,
            String envName,
            String envOptions,
            long steps,
            int storeId,
            Optional<SzenarioExecutionObserver> observer) throws StartAgentTrainingException;

    void startEvaluation(
            String agentName,
            String envName,
            String envOptions,
            long steps,
            int storeId,
            Optional<SzenarioExecutionObserver> observer) throws StartAgentTrainingException;

}
