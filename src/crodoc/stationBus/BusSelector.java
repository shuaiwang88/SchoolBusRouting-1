package crodoc.stationBus;

import crodoc.Problem;
import crodoc.evaluator.ListEvaluator;
import crodoc.solution.Bus;
import crodoc.solution.ListSolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BusSelector {
    public static ListSolution selectStations(ListSolution s, Problem p) {
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

        List<Dot> ld = new ArrayList<>();

        for (int stop : stops) {
            double x = p.stopX[stop] - p.stopX[0];
            double y = p.stopY[stop] - p.stopY[0];
            ld.add(new Dot(stop, Math.atan2(x,y)));
        }

        Collections.sort(ld);

        for (int i = 0; i < stops.length; i++) {
            stops[i] = ld.get(i).who;
        }

        int[] stopTmp = new int[p.stops];

        for (int stop : s.students) {
            stopTmp[stop]++;
        }

        int[] stopCnt = new int[stops.length];

        for (int i = 0; i < stopCnt.length; i++) {
            stopCnt[i] = stopTmp[stops[i]];
        }

        List<Integer> full = new ArrayList<>();
        for (int i = 0; i < stops.length; i++) {
            if (stopCnt[i] == p.capacity) {
                full.add(stops[i]);
            }
        }

        int[] nStops = new int[stops.length-full.size()];
        int[] nStopsCnt = new int[stops.length-full.size()];
        int pp = 0;
        for (int i = 0; i < stops.length; i++) {
            if (!full.contains(stops[i])) {
                nStops[pp] = stops[i];
                nStopsCnt[pp] = stopCnt[i];
                pp++;
            }
        }

        stops = nStops;
        stopCnt = nStopsCnt;

        bst.setFitness(1000000000033.0);

        // TU JEDANAEST
        /*
        if (stops.length <= 10) {
            permute(s, stopCnt, stops, p, stops.length, full);
            return bst;
        }*/

        s.buses = realDP(s, stopCnt, stops, p);

        addfull(s, full);

        ListEvaluator le = new ListEvaluator(p);
        le.evaluate(s);

        bst = s.copy();

        ListSolution ss = s.copy();
        for (int z = 0; z < stops.length && z < 7;z++) {
            s = ss.copy();

            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < stops.length; j++) {
                    for (int k = 1; k <= 7; k++) {
                        if (j+k < stops.length) {
                            swap(stopCnt, stops, j, j+k);
                            s.buses = realDP(s, stopCnt, stops, p);
                            addfull(s, full);
                            le.evaluate(s);

                            if (s.compareTo(bst) <= 0) {
                                bst = s.copy();
                                //System.out.println(bst.getFitness());
                            } else {
                                swap(stopCnt, stops, j, j+k);
                            }
                        }
                    }
                }
            }


            for (int j = 1; j < stops.length; j++) {
                swap(stopCnt, stops, j, j-1);
            }
        }

        /*
        for (int i = 0; i < stops.length; i++) {

            //s.buses = busDP(s, stops, p);
            s.buses = realDP(s,stopCnt, stops,p);

            addfull(s, full);

            ListEvaluator le = new ListEvaluator(p);
            le.evaluate(s);

            bst = Math.min(bst, s.getFitness());

            /*

            List<Integer> ll = new ArrayList<>();

            for (int x : stops) {
                ll.add(x);
            }

            Collections.shuffle(ll);

            for (int j = 0; j < stops.length; j++) {
                stops[j] = ll.get(j);
            } kraj/ *

            int tmp = stops[0];

            for (int j = 1; j < stops.length; j++) {
                stops[j-1] = stops[j];
            }
            stops[stops.length-1] = tmp;
        }*/

        return bst;
    }

    private static void addfull(ListSolution s, List<Integer> full) {

        int n = s.buses.length;
        int m = n + full.size();

        Bus[] nb = new Bus[m];

        for (int i = 0; i < n; i++) {
            nb[i] = s.buses[i];
        }

        for (int i = n; i < m; i++) {
            nb[i] = new Bus();
            nb[i].stations.add(full.get(i-n));
        }

        s.buses = nb;
    }

    public static class Dot implements Comparable<Dot> {
        int who;
        double angle;

        public Dot(int who, double angle) {
            this.who = who;
            this.angle = angle;
        }

        @Override
        public int compareTo(Dot o) {

            if (angle < o.angle) {
                return -1;
            }

            if (angle > o.angle) {
                return 1;
            }

            return 0;
        }
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

    public static Bus[] realDP(ListSolution s, int[] stopCnt, int[] stops, Problem p) {

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

    private static void swap(int[] a, int[] b, int x, int y) {
        int tmp = a[x]; a[x] = a[y]; a[y] = tmp;
        tmp = b[x]; b[x] = b[y]; b[y] = tmp;
    }

    private static ListSolution bst = new ListSolution(0);
    private static void permute(ListSolution s, int[] cnt, int[] stops, Problem p, int n, List<Integer> full) {
        if (n == 1) {
            s.buses = realDP(s,cnt, stops,p);

            addfull(s, full);

            ListEvaluator le = new ListEvaluator(p);
            le.evaluate(s);

            if (bst.compareTo(s) > 0) {
                bst = s.copy();
            }

            return;
        }
        for (int i = 0; i < n; i++) {
            swap(cnt, stops, i, n-1);
            permute(s, cnt, stops, p, n-1, full);
            swap(cnt, stops, i, n-1);
        }
    }
}
