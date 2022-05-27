package de.jquast.plugin.commands;

import de.jquast.adapters.facade.ConfigServiceFacade;
import de.jquast.adapters.facade.ExecutionServiceFacade;
import de.jquast.adapters.facade.dto.ConfigItemDto;
import de.jquast.application.config.DefaultConfigItem;
import de.jquast.application.exception.StartSzenarioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RunCommandTest {

    @Mock
    private ExecutionServiceFacade executionServiceFacade;

    @Mock
    private ConfigServiceFacade configServiceFacade;

    private RunCommand command;

    @BeforeEach
    public void prepare() {
        MockitoAnnotations.openMocks(this);

        when(configServiceFacade.getConfigItem(eq(DefaultConfigItem.MESSAGE_TRAINING_AVERAGE_REWARD_STEPS))).thenReturn(new ConfigItemDto(
                DefaultConfigItem.MESSAGE_TRAINING_AVERAGE_REWARD_STEPS.getKey(),
                DefaultConfigItem.MESSAGE_TRAINING_AVERAGE_REWARD_STEPS.getDefaultValue()
        ));

        command = new RunCommand(executionServiceFacade, configServiceFacade);
        command.agentName = "nice";
        command.environmentName = "nice";
        command.environmentOptions = "options";
        command.steps = 100000L;
        command.resumeFromStoreId = 10;
        command.evalMode = false;
    }

    @Test
    public void commandShouldCallStartTrainingWhenEvalOptionIsNotSet() throws StartSzenarioException {
        command.evalMode = false;
        command.run();

        verify(executionServiceFacade).startTraining(
                eq("nice"),
                eq("nice"),
                eq("options"),
                eq(100000L),
                eq(10),
                any()
        );
    }

    @Test
    public void commandShouldCallStartEvaluationWhenEvalOptionIsSet() throws StartSzenarioException {
        command.evalMode = true;
        command.run();

        verify(executionServiceFacade).startEvaluation(
                eq("nice"),
                eq("nice"),
                eq("options"),
                eq(100000L),
                eq(10),
                any()
        );
    }

}
