package crodoc.generic;

import crodoc.solution.Solution;

public interface IMutation<T extends Solution> {
    void mutate(T solution);
}