package de.jquast.domain.policy;

public interface Policy {
    int chooseAction(double[] estimatedActionUtility);
}
