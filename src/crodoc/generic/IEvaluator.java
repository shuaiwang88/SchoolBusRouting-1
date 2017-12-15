package crodoc.generic;

public interface IEvaluator<T extends Solution> {
    void evaluate(T solution);
}
