package crodoc.stationBus;

import crodoc.Problem;
import crodoc.solution.Bus;
import crodoc.solution.ListSolution;

import java.util.ArrayList;
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

        s.buses = busDP(s, stops, p);
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
}
