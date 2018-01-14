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
        return null;
    }
}
