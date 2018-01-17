package crodoc;

import crodoc.analysis.Analizator;
import crodoc.evaluator.ListEvaluator;
import crodoc.solution.ListSolution;
import crodoc.stationBus.BusSelector;
import crodoc.stationBus.StationSelector;

public class Main {
    public static void main(String[] args) {
        /*
        for (int i = 1; i <= 10; i++) {
            Analizator a = new Analizator("sbr" + i + ".txt");
            a.analyze();
        }*/

        for (int i = 2; i <= 2; i++) {

            Problem p = new Problem("sbr" + i + ".txt");
            ListSolution s = StationSelector.selectStations(p);
            BusSelector.selectStations(s, p);

            ListEvaluator le = new ListEvaluator(p);
            le.evaluate(s);

            //System.out.println(i + " " + s.getFitness());
            System.out.println(s.ToStr());
        }
    }
}
