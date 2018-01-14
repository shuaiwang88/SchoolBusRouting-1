package crodoc.generic;

import crodoc.solution.Solution;

public interface ICrossover<T extends Solution> {
    T crossover(T parent1, T parent2);
}