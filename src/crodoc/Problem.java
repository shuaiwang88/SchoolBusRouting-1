package crodoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem {
    public int stops;
    public int students;
    public double distance;
    public int capacity;

    public double[] stopX;
    public double[] stopY;
    public double[] studentX;
    public double[] studentY;

    public Problem(String filename) {
        Scanner sc;

        try {
            sc = new Scanner(new File("input/"+filename));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("file error");
        }

        stops = sc.nextInt(); sc.next();
        students = sc.nextInt(); sc.next();
        distance = sc.nextDouble(); sc.next(); sc.next();
        capacity = sc.nextInt(); sc.next();

        stopX = new double[stops];
        stopY = new double[stops];

        for (int i = 0; i < stops; i++) {
            sc.nextInt();
            stopX[i] = sc.nextDouble();
            stopY[i] = sc.nextDouble();
        }

        studentX = new double[students];
        studentY = new double[students];

        for (int i = 0; i < students; i++) {
            sc.nextInt();
            studentX[i] = sc.nextDouble();
            studentY[i] = sc.nextDouble();
        }
    }
}
