package de.jquast.domain.algorithm;

import de.jquast.domain.policy.ActionSource;
import de.jquast.domain.policy.Policy;

public abstract class RLAlgorithm implements ActionSource {

    private Policy basePolicy;

    /**
     * Gibt die verwendete Policy des Algorithmus zurück.
     *
     * @return Die verwendete Policy, sofern der Algorithmus ein on-Policy Algorithmus ist. NULL andernfalls.
     */
    public Policy getPolicy() {
        return basePolicy;
    }

}
