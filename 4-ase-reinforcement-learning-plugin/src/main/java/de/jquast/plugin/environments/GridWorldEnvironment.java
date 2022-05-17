package de.jquast.plugin.environments;

import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.Action;

public class GridWorldEnvironment extends Environment {

    private int height;
    private int width;

    private int currX;
    private int currY;
    private int terminalState;

    public GridWorldEnvironment(int height, int width) {
        this.height = height;
        this.width = width;

        terminalState = squashTo1DCoordinate(width - 2, height - 2);
        currX = 0;
        currY = 0;
    }

    @Override
    public boolean executeAction(Action action, int data) {
        if (action.equals(Action.MOVE_X_UP) && currX + 1 < width) {
            currX += 1;
            return true;
        }

        if (action.equals(Action.MOVE_X_DOWN) && currX - 1 >= 0) {
            currX -= 1;
            return true;
        }

        if (action.equals(Action.MOVE_Y_UP) && currY + 1 < height) {
            currX += 1;
            return true;
        }

        if (action.equals(Action.MOVE_Y_DOWN) && currY - 1 >= 0) {
            currY -= 1;
            return true;
        }

        return true;
    }

    @Override
    public int getCurrentState() {
        return squashTo1DCoordinate(currX, currY);
    }

    @Override
    public int getStateSpace() {
        return height * width;
    }

    @Override
    public void tick() {
        if (squashTo1DCoordinate(currX, currY) == terminalState) {
            currY = 0;
            currX = 0;
        }
    }

    @Override
    public double getReward() {
        return -0.1;
    }

    private int squashTo1DCoordinate(int x, int y) {
        return (y * width) + x;
    }
}
