package crodoc.evaluator;

import crodoc.solution.Solution;

public interface IEvaluator<T extends Solution> {
    void evaluate(T solution);
}
