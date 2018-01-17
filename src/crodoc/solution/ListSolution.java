package crodoc.solution;

public class ListSolution extends Solution{
    public int[] students;
    public int[] stops;
    public Bus[] buses;

    public ListSolution(int[] students, int[] stops) {
        this.students = students;
        this.stops = stops;
    }

    public ListSolution(int studentCount, int stopCount) {
        students = new int[studentCount];
        stops = new int[stopCount];
    }

    @Override
    public Solution copy() {
        return null;
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
