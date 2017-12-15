package crodoc.selection;

import crodoc.generic.Solution;
import java.util.List;

public interface ISelection<T extends Solution> {
        T select(List<T> population);
}
