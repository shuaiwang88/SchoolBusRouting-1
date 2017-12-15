package crodoc.generic;

public interface IMutation<T extends Solution> {
    void mutate(T solution);
}