package crodoc.evaluator;

import crodoc.Problem;
import crodoc.solution.Bus;
import crodoc.solution.ListSolution;

public class ListEvaluator implements IEvaluator<ListSolution> {

    private Problem p;

    public ListEvaluator(Problem problem) {
        this.p = problem;
    }
    public static int hm = 0;

    @Override
    public void evaluate(ListSolution solution) {

        hm++;
        double dist = 0.0;

        for(Bus b : solution.buses) {
            dist += stationDistance(0, b.stations.get(0));
            dist += stationDistance(b.stations.get(b.stations.size()-1), 0);

            for (int i = 1; i < b.stations.size(); i++) {
                dist += stationDistance(b.stations.get(i-1), b.stations.get(i));
            }
        }

        solution.setFitness(dist);
    }

    private double stationDistance(int st1, int st2) {
            return Math.sqrt((p.stopX[st1]-p.stopX[st2])*(p.stopX[st1]-p.stopX[st2])+(p.stopY[st1]-p.stopY[st2])*(p.stopY[st1]-p.stopY[st2]));
    }
}
