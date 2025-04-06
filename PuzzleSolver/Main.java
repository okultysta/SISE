package PuzzleSolver;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Użycie:");
            System.out.println("Dla BFS/DFS: program <bfs|dfs> <ROZKAZ> <plik_we> <plik_sol> <plik_stat>");
            System.out.println("Dla A*: program astr <manh|hamm> <plik_we> <plik_sol> <plik_stat>");
            return;
        }

        String algorithm = args[0];
        String param = args[1];
        String inputPath = args[2];
        String solutionPath = args[3];  // To już nie będzie używane
        String statsPath = args[4];  // To również nie będzie używane

        try {
            // Wczytaj dane
            BufferedReader reader = new BufferedReader(new FileReader(inputPath));
            String[] dimensions = reader.readLine().split(" ");
            int height = Integer.parseInt(dimensions[0]);
            int width = Integer.parseInt(dimensions[1]);

            int[][] startBoard = new int[height][width];
            for (int i = 0; i < height; i++) {
                String[] line = reader.readLine().split(" ");
                for (int j = 0; j < width; j++) {
                    startBoard[i][j] = Integer.parseInt(line[j]);
                }
            }
            reader.close();

            // Przykładowe wywołanie algorytmu (tu np. BFS) dla wczytanego stanu
            List<String> result = new ArrayList<>();

            switch (algorithm.toLowerCase()) {
                case "bfs":
                    result = Algorithms.solveBFS(startBoard, width, height);
                    break;
                case "dfs":
                    int maxDepth = 30; // Można parametr ustawić dynamicznie
                    result = Algorithms.solveDFS(startBoard, width, height, maxDepth);
                    break;
                case "astr":
                    boolean useHamming;
                    if (param.equalsIgnoreCase("hamm")) {
                        useHamming = true;
                    } else if (param.equalsIgnoreCase("manh")) {
                        useHamming = false;
                    } else {
                        System.out.println("Nieznana heurystyka: " + param);
                        return;
                    }
                    result = Heuristics.solveHeuristics(startBoard, width, height, useHamming);
                    break;
                default:
                    System.out.println("Nieznany algorytm: " + algorithm);
                    return;
            }

            // Tutaj możesz dodać kod do dalszego wykorzystania rozwiązania (np. wyświetlenie wyników)
            System.out.println("Rozwiązanie: " + (result.isEmpty() ? "Brak rozwiązania" : String.join(" ", result)));

        } catch (IOException e) {
            System.out.println("Błąd przy wczytywaniu pliku: " + e.getMessage());
        }
    }
}
