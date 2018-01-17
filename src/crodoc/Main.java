package crodoc;

import crodoc.analysis.Analizator;
import crodoc.evaluator.ListEvaluator;
import crodoc.solution.ListSolution;
import crodoc.stationBus.BusSelector;
import crodoc.stationBus.SparseStationSelector;
import crodoc.stationBus.StationSelector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        /*
        for (int i = 3; i <= 3; i++) {
            Analizator a = new Analizator("sbr" + i + ".txt");
            a.analyze();
        }*/


        int i = Integer.parseInt(args[0]);

        double bst = 100000454540.0;

        for (int k = 0; ;k++) {
         {
            Problem p = new Problem("sbr" + i + ".txt");

            ListSolution s = null;
            while (s == null) {
                s = StationSelector.selectStations(p);
            }

            s = BusSelector.selectStations(s, p);


            if (s.getFitness() < bst) {
                System.out.println(k + " " + s.getFitness());
                bst = s.getFitness();

                Scanner sc;
                double fit = 10000000000.0;

                try {
                    sc = new Scanner(new File("result/info"+i+".txt")) ;
                    fit = Double.parseDouble(sc.next());

                } catch (Exception e) {

                }

                if (fit <= s.getFitness()) {
                    continue;
                }
                System.out.println("BEST");

                PrintWriter out;

                try {
                    out = new PrintWriter("result/res-ne-sbr" + i + ".txt");
                } catch (FileNotFoundException e) {
                    throw new IllegalArgumentException();
                }

                out.print(s.ToStr());
                out.close();

                try {
                    out = new PrintWriter("result/info" + i + ".txt");
                } catch (FileNotFoundException e) {
                    throw new IllegalArgumentException();
                }

                out.println(s.getFitness());
                out.println(ListEvaluator.hm);
                out.close();
            }
            //System.out.println(s.ToStr());
        }
        }
    }
}
