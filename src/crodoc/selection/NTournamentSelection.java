package crodoc.selection;

import crodoc.solution.Solution;

import java.util.List;
import java.util.Random;

public class NTournamentSelection<T extends Solution> implements ISelection<T> {
    private int n;
    private Random rand = new Random();

    public NTournamentSelection(int n) {
        this.n = n;
    }

    @Override
    public T select(List<T> population) {
        T res = population.get(rand.nextInt(population.size()));
        T tmp;

        for (int i = 0; i < n; i++) {
            tmp = population.get(rand.nextInt(population.size()));

            if (tmp.compareTo(res) < 0) {
                res = tmp;
            }
        }

        return res;
    }
}
