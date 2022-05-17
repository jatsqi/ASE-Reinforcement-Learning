package de.jquast.domain.policy;

import de.jquast.domain.environment.Action;

public interface ActionSource {

    Action selectAction(int state);

}
