package de.jquast.domain.shared;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum Action {

    DO_NOTHING(-1, "Faulenzen und nichts tun"),
    MOVE_X_UP(0, "Bewegung in positive X Richtung"),
    MOVE_Y_UP(1, "Bewegung in positive Y Richtung"),
    MOVE_Z_UP(2, "Bewegung in positive Z Richtung"),
    MOVE_X_DOWN(3, "Bewegung in negative X Richtung"),
    MOVE_Y_DOWN(4, "Bewegung in negative Y Richtung"),
    MOVE_Z_DOWN(5, "Bewegung in negative Z Richtung"),
    PULL(6, "Ziehe am Hebel");

    private int id;
    private String description;

    Action(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Action[] computeMissingRequirements(Action[] capabilities, Action[] requirements) {
        Set<Action> capSet = new HashSet<>(Arrays.asList(capabilities));
        Set<Action> reqSet = new HashSet<>(Arrays.asList(requirements));

        reqSet.removeAll(capSet);
        return reqSet.toArray(new Action[0]);
    }
}
