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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        /*
        for (int i = 3; i <= 3; i++) {
            Analizator a = new Analizator("sbr" + i + ".txt");
            a.analyze();
        }*/


        int i = Integer.parseInt(args[0]);

        //for (int i = 5; i <= 8; i++) {

        double bst = 100000454540.0;

        long t = System.currentTimeMillis();

        while (true) {
        //for (int k = 0; System.currentTimeMillis() - t < 60*1000;k++) {
         {
            Problem p = new Problem("sbr" + i + ".txt");

            List<Integer> ls = new ArrayList<>();
            for (int ll = 0; ll < 100; ll++) {
                ls.add(ll);
            }

            List<ListSolution> lss = ls.parallelStream().map((ppp) -> {
                ListSolution ss2 = null;
                while (ss2 == null) {
                    ss2 = StationSelector.selectStations(p);
                }

                ss2 = BusSelector.selectStations(ss2, p);

                return ss2;

            }).sorted().collect(Collectors.toList());

            ListSolution s = lss.get(0);

            if (s.getFitness() < bst) {
                System.out.println(i + " " + s.getFitness());
                bst = s.getFitness();

                Scanner sc;
                double fit = 10000000000.0;

                try {
                    sc = new Scanner(new File("result/info-ne"+i+".txt")) ;
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
                    out = new PrintWriter("result/info-ne" + i + ".txt");
                } catch (FileNotFoundException e) {
                    throw new IllegalArgumentException();
                }

                out.println(s.getFitness());
                out.println(ListEvaluator.hm);
                out.close();
            }
            //System.out.println(s.ToStr());
        }
        }//}
    }
}
