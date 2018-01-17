package crodoc.solution;

public class ListSolution extends Solution{
    public int[] students;
    public Bus[] buses;

    public ListSolution(int[] students, Bus[] buses) {
        this.students = students;
        this.buses = buses;
    }

    public ListSolution(int studentCount) {
        students = new int[studentCount];
    }

    @Override
    public ListSolution copy() {
        int[] s2 = new int[students.length];
        Bus[] b2 = new Bus[buses.length];

        for (int i = 0; i < s2.length; i++) {
            s2[i] = students[i];
        }

        for (int i = 0; i < b2.length; i++) {
            b2[i] = new Bus();

            b2[i].stations.addAll(buses[i].stations);
        }

        ListSolution res = new ListSolution(s2, b2);
        res.setFitness(getFitness());

        return res;
    }

    @Override
    public String ToStr() {
        StringBuilder b = new StringBuilder();

        for (Bus bus : buses) {
            for (int i = 0; i < bus.stations.size(); i++) {
                if (i != 0) {
                    b.append(" ");
                }
                b.append(bus.stations.get(i));
            }
            b.append("\n");
        }

        b.append("\n");

        for (int i = 0; i < students.length; i++) {
            b.append(i+1 + " " + students[i] + "\n");
        }

        return b.toString();
    }
}