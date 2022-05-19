package de.jquast.plugin.environments;

import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.Action;

public class GridWorldEnvironment extends Environment {

    public static final int STATE_NORMAL = 0;
    public static final int STATE_TERMINAL = 1;
    public static final int STATE_FORBIDDEN = 2;

    private int height;
    private int width;
    private int currX;
    private int currY;
    private int[][] grid;

    private int stepsInEpisode = 0;

    public GridWorldEnvironment(int height, int width) {
        this(new int[width][height]);

        this.grid[width - 1][height - 1] = STATE_TERMINAL;
    }

    public GridWorldEnvironment(int[][] grid) {
        this.width = grid.length;
        this.height = grid[0].length;

        this.currX = 0;
        this.currY = 0;

        this.grid = grid;
    }

    @Override
    public boolean executeAction(Action action, int data) {
        int oldX = currX;
        int oldY = currY;

        if (action.equals(Action.MOVE_X_UP) && currX + 1 < width) {
            currX += 1;
        }

        if (action.equals(Action.MOVE_X_DOWN) && currX - 1 >= 0) {
            currX -= 1;
        }

        if (action.equals(Action.MOVE_Y_UP) && currY + 1 < height) {
            currY += 1;
        }

        if (action.equals(Action.MOVE_Y_DOWN) && currY - 1 >= 0) {
            currY -= 1;
        }

        if (isForbiddenState()) {
            currX = oldX;
            currY = oldY;
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
        stepsInEpisode++;

        if (isTerminalState() || stepsInEpisode >= 1000) {
            currX = 0;
            currY = 0;
            stepsInEpisode = 0;
        }
    }

    @Override
    public double getReward() {
        if (isTerminalState()) {
            return 5;
        }

        return -0.01;
    }

    private int squashTo1DCoordinate(int x, int y) {
        return (y * width) + x;
    }

    private boolean isTerminalState() {
        return grid[currX][currY] == STATE_TERMINAL;
    }

    private boolean isForbiddenState() { return grid[currX][currY] == STATE_FORBIDDEN; }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
