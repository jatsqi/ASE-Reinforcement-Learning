package de.jquast.adapters.facade.impl;

import de.jquast.adapters.facade.ExecutionServiceFacade;
import de.jquast.application.exception.StartAgentTrainingException;
import de.jquast.application.service.ExecutionService;
import de.jquast.application.service.SzenarioExecutionObserver;

import java.util.Optional;

public class ExecutionServiceFacadeImpl implements ExecutionServiceFacade {

    private final ExecutionService service;

    public ExecutionServiceFacadeImpl(ExecutionService service) {
        this.service = service;
    }

    @Override
    public void startTraining(String agentName, String envName, String envOptions, long steps, int storeId, Optional<SzenarioExecutionObserver> observer) throws StartAgentTrainingException {
        service.startTraining(
                agentName,
                envName,
                envOptions,
                steps,
                storeId,
                observer
        );
    }

    @Override
    public void startEvaluation(String agentName, String envName, String envOptions, long steps, int storeId, Optional<SzenarioExecutionObserver> observer) throws StartAgentTrainingException {
        service.startEvaluation(
                agentName,
                envName,
                envOptions,
                steps,
                storeId,
                observer
        );
    }
}
