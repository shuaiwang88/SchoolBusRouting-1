package crodoc.stationBus;

import crodoc.Problem;
import crodoc.solution.ListSolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class StationSelector {
    public static ListSolution selectStations(Problem p) {
        int stops = p.stops-1;
        int students = p.students;

        boolean stopVisited[] = new boolean[stops+1];

        ListSolution s = new ListSolution(students);

        List<Integer> sortedStops = new ArrayList<>();
        for (int i = 1; i <= stops; i++) {
            sortedStops.add(i);
        }

        sortedStops.sort((Integer a, Integer b) -> sDiff(a,b,p));
        Random rand = new Random();

        List<Integer> sts = new ArrayList<>();

        for (int i = 0; i < students; i++) {
            sts.add(i);
        }

        int cnt2 = 0;
        while (students != 0) {
            int who = -1;
            int cnt = 0;
            boolean nxt = false;

            int tmp;
            for (int index = 0; index < stops; index++) {
                if (rand.nextDouble() < 0.2) {
                    continue;
                }
                int st = sortedStops.get(index);
                if (stopVisited[st]) {
                    continue;
                }

                tmp = 0;

                for (int id = 0; id < p.students; id++) {
                    if (s.students[id] == 0 && studentDist(id, st, p) <= p.distance) {
                        tmp++;
                    }
                }

                if (tmp >= p.capacity) {
                    nxt = true;
                    tmp = p.capacity;

                    stopVisited[st] = true;

                    List<Integer> ss = new ArrayList<>();

                    for (int id = 0; id < p.students; id++) {
                        if (s.students[id] == 0 && studentDist(id, st, p) <= p.distance) {

                            ss.add(id);
                        }
                    }

                    Collections.shuffle(ss);

                    for (int id = 0; id < tmp; id++) {
                        s.students[ss.get(id)] = st;
                        students--;
                    }

                    break;
                }

                if (tmp > cnt) {
                    cnt = tmp;
                    who = st;
                }
            }

            if (cnt2++ == 100) {
                return null;
            }

            if (who == -1) {
               continue;
            }

            if (!nxt) {
                stopVisited[who] = true;
                Collections.shuffle(sts);
                for (int id : sts) {
                //for (int id = 0; id < p.students; id++) {
                    if (s.students[id] == 0 && studentDist(id, who, p) <= p.distance) {
                        s.students[id] = who;
                        students--;
                    }
                }
            }
        }

        return s;
    }

    private static int sDiff(int a, int b, Problem p) {
        double d = sDist(a,0,p)-sDist(b,0,p);

        if (d < 0) {
            return -1;
        }

        if (d > 0) {
            return 1;
        }

        return 0;
    }

    private static double sDist(int st1, int st2, Problem p) {
        return Math.sqrt((p.stopX[st1]-p.stopX[st2])*(p.stopX[st1]-p.stopX[st2])+(p.stopY[st1]-p.stopY[st2])*(p.stopY[st1]-p.stopY[st2]));
    }

    private static double studentDist(int student, int stop, Problem p) {
        return Math.sqrt((p.stopX[stop]-p.studentX[student])*(p.stopX[stop]-p.studentX[student])+(p.stopY[stop]-p.studentY[student])*(p.stopY[stop]-p.studentY[student]));
    }
}
