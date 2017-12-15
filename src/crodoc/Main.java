package crodoc;

import crodoc.analysis.Analizator;

public class Main {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            Analizator a = new Analizator("sbr" + i + ".txt");
            a.analyze();
        }
    }
}
