package crodoc.stationBus;

import crodoc.Problem;
import crodoc.evaluator.ListEvaluator;
import crodoc.solution.Bus;
import crodoc.solution.ListSolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BusSelector {
    public static void selectStations(ListSolution s, Problem p) {
        boolean[] stopTaken = new boolean[p.stops];

        int cnt = 0;

        for (int x : s.students) {
            if (!stopTaken[x]) {
                cnt++;
            }

            stopTaken[x] = true;
        }

        int[] stops = new int[cnt];
        cnt = 0;

        stopTaken = new boolean[p.stops];

        for (int x : s.students) {
            if (!stopTaken[x]) {
                stops[cnt] = x;
                cnt++;
                stopTaken[x] = true;
            }
        }

        double bst = 1000000000033.0;

        for (int i = 0; i < 10000000; i++) {

            //s.buses = busDP(s, stops, p);
            s.buses = realDP(s,stops,p);

            ListEvaluator le = new ListEvaluator(p);
            le.evaluate(s);

            bst = Math.min(bst, s.getFitness());

            List<Integer> ll = new ArrayList<>();

            for (int x : stops) {
                ll.add(x);
            }

            Collections.shuffle(ll);

            for (int j = 0; j < stops.length; j++) {
                stops[j] = ll.get(j);
            }
        }

        System.out.println(bst);
    }

    public static Bus[] busDP(ListSolution s, int[] stops, Problem p) {
        List<Bus> lb = new ArrayList<>();

        int[] stopCnt = new int[p.stops];

        for (int stop : s.students) {
            stopCnt[stop]++;
        }

        lb.add(new Bus());
        int hm = 0;

        for (int stop : stops) {
            if (hm + stopCnt[stop] > p.capacity) {
                hm = 0;
                lb.add(new Bus());
            }

            hm += stopCnt[stop];
            lb.get(lb.size()-1).stations.add(stop);
        }

        Bus[] res = new Bus[lb.size()];

        for (int i = 0; i < res.length; i++) {
            res[i] = lb.get(i);
        }

        return res;
    }

    public static Bus[] realDP(ListSolution s, int[] stops, Problem p) {

        int[] stopTmp = new int[p.stops];

        for (int stop : s.students) {
            stopTmp[stop]++;
        }

        int[] stopCnt = new int[stops.length];

        for (int i = 0; i < stopCnt.length; i++) {
            stopCnt[i] = stopTmp[stops[i]];
        }

        double dp[] = new double[stops.length+1];
        for (int i = 0; i < dp.length; i++) {
            dp[i] = 10000000000.0;
        }

        dp[0] = 0;

        int idx[] = new int[stops.length+1];

        for (int i = 1; i <= stops.length; i++) {
            int hm = 0;
            double cost = stationDistance(0, stops[i-1], p);

            for (int j = i; j >= 1; j--) {
                hm += stopCnt[j-1];
                if (hm > p.capacity) {
                    break;
                }

                if (j != i) {
                    cost += stationDistance(stops[j-1], stops[j], p);
                }

                if (dp[i] > dp[j-1] + cost + stationDistance(stops[j-1], 0, p)) {
                    dp[i] = dp[j-1] + cost + stationDistance(stops[j-1], 0, p);
                    idx[i] = idx[j-1]+1;
                }
            }
        }

        int n = stops.length;

        Bus[] res = new Bus[idx[n]];

        for (int i = 0; i < res.length; i++) {
            res[i] = new Bus();
        }


        for (int i = 0; i < n; i++) {
            res[idx[i+1]-1].stations.add(stops[i]);
        }

        return res;
    }

    private static double stationDistance(int st1, int st2, Problem p) {
        return Math.sqrt((p.stopX[st1]-p.stopX[st2])*(p.stopX[st1]-p.stopX[st2])+(p.stopY[st1]-p.stopY[st2])*(p.stopY[st1]-p.stopY[st2]));
    }

}
