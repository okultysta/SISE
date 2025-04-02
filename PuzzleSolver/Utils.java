package PuzzleSolver;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

public class Utils {

    public static State readStateFromFile(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String firstLine = br.readLine();
            if (firstLine == null) {
                throw new IOException("Plik jest pusty");
            }

            String[] dimensions = firstLine.split(" ");
            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);

            int[][] puzzle = new int[rows][cols];

            for (int i = 0; i < rows; i++) {
                String line = br.readLine();
                if (line == null) {
                    throw new IOException("Nieoczekiwany koniec pliku");
                }

                String[] values = line.split(" ");
                if (values.length != cols) {
                    throw new IOException("Nieprawidłowa liczba kolumn w wierszu " + (i + 1));
                }

                for (int j = 0; j < cols; j++) {
                    puzzle[i][j] = Integer.parseInt(values[j]);
                }
            }

            return new State(puzzle, puzzle.length, puzzle[0].length, new ArrayList<>());
        }
    }
    public static void writeSolutionFile(String filename, int solutionLength, String moves) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(Integer.toString(solutionLength));
            bw.newLine();
            if (solutionLength != -1) {
                bw.write(moves);
            }
        }
    }

    public static void writeAdditionalInfoFile(String filename, int solutionLength, int visitedStates, int processedStates, int maxDepth, double computationTime) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(Integer.toString(solutionLength));
            bw.newLine();
            bw.write(Integer.toString(visitedStates));
            bw.newLine();
            bw.write(Integer.toString(processedStates));
            bw.newLine();
            bw.write(Integer.toString(maxDepth));
            bw.newLine();
            bw.write(String.format(Locale.US, "%.3f", computationTime));
        }
    }
}
