package crodoc;

import crodoc.analysis.Analizator;
import crodoc.solution.ListSolution;
import crodoc.stationBus.StationSelector;

public class Main {
    public static void main(String[] args) {
        /*
        for (int i = 1; i <= 10; i++) {
            Analizator a = new Analizator("sbr" + i + ".txt");
            a.analyze();
        }*/

        Problem p = new Problem("sbr5.txt");
        ListSolution s = StationSelector.selectStations(p);

        System.out.println(s);
    }
}
