package de.jquast.domain.algorithm;

import de.jquast.domain.policy.Policy;

public interface RLAlgorithm {

    /**
     * Gibt den Namen des Algorithmus zurück.
     * @return Der Name des Algorithmus.
     */
    String getName();

    /**
     * Gibt die Beschreibung des Algorithmus zurück.
     * @return Die Beschreibung des Algorithmus.
     */
    String getDescription();

    /**
     * Gibt die verwendete Policy des Algorithmus zurück.
     * @return Die verwendete Policy, sofern der Algorithmus ein on-Policy Algorithmus ist. NULL andernfalls.
     */
    Policy getPolicy();

    /**
     *
     * @return
     */
    int getTimeSteps();

}
