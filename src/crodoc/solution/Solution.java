package crodoc.solution;

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

    @Override
    public int compareTo(Solution o) {
        if (fitness < o.fitness) {
            return -1;
        }

        if (fitness > o.fitness) {
            return 1;
        }

        return 0;
    }
}

