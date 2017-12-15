package crodoc.generic;

public abstract class Solution implements Comparable<Solution> {

    protected double fitness;

    public Solution() {
    }

    public Solution(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public abstract Solution copy();
    public abstract String ToStr();
}

