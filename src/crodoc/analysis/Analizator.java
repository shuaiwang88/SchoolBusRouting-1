package crodoc.analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Analizator {
    private String filename;

    public Analizator(String filename) {
        this.filename = filename;
    }

    public void analyze() {
        Scanner sc;

        try {
            sc = new Scanner(new File("input/"+filename));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("file error");
        }

        int stops = sc.nextInt(); sc.next();
        int students = sc.nextInt(); sc.next();
        double maxDistance = sc.nextDouble(); sc.next(); sc.next();
        int capacity = sc.nextInt(); sc.next();

        double[] xs = new double[stops], ys = new double[stops];

        for (int i = 0; i < stops; i++) {
            sc.nextInt();
            xs[i] = sc.nextDouble();
            ys[i] = sc.nextDouble();
        }

        int jedan = 0, sve = 0;

        for (int i = 0; i < students; i++) {
            sc.nextInt();
            double x = sc.nextDouble(), y = sc.nextDouble();

            int cnt = 0;

            for (int j = 0; j < stops; j++) {
                if (Math.sqrt((xs[j]-x)*(xs[j]-x)+(ys[j]-y)*(ys[j]-y)) <= maxDistance) {
                    cnt++;
                }
            }

            if (cnt == 1) {
                jedan++;
            }

            if (cnt == 81) {
                sve++;
            }

            System.out.print(cnt + " ");
        }
        System.out.println();
        System.out.println(jedan + " " + sve);
    }
}
