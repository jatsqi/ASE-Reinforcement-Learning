package de.jquast.domain.shared;

public class ActionValueStore {

    private double[][] actionValueEstimates;
    private final int stateCount;
    private final int actionCount;

    public ActionValueStore(double[][] prevActionValueEstimates) {
        this(prevActionValueEstimates, prevActionValueEstimates.length, prevActionValueEstimates[0].length);
    }

    public ActionValueStore(int stateCount, int actionCount) {
        this(new double[stateCount][actionCount], stateCount, actionCount);
    }

    private ActionValueStore(double[][] prevActionValueEstimates, int stateCount, int actionCount) {
        this.actionValueEstimates = prevActionValueEstimates;
        this.stateCount = stateCount;
        this.actionCount = actionCount;
    }

    public double getActionValue(int state, int action) {
        return actionValueEstimates[state][action];
    }

    public double[] getActionValues(int state) {
        return actionValueEstimates[state];
    }

    public ActionValueEntry getMaxActionValue(int state) {
        double maxValue = Double.MIN_VALUE;
        int maxAction = 0;

        for (int i = 0; i < actionValueEstimates[state].length; ++i) {
            if (actionValueEstimates[state][i] > maxValue) {
                maxValue = actionValueEstimates[state][i];
                maxAction = i;
            }
        }

        return new ActionValueEntry(state, maxAction, maxValue);
    }

    public void setActionValue(int state, int action, double value) {
        actionValueEstimates[state][action] = value;
    }

    public void reset() {
        actionValueEstimates = new double[stateCount][actionCount];
    }

    public int getStateCount() {
        return stateCount;
    }

    public int getActionCount() {
        return actionCount;
    }

    public record ActionValueEntry(int state, int action, double value) { }
}
