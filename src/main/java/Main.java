import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Inicjalizacja układanki 15 (rozmiar 4x4)
        int[][] startState = {
                {5, 1, 3, 4},
                {9, 2, 6, 8},
                {13, 14, 7, 11},
                {0, 15, 10, 12}
        };

        int width = 4; // szerokość planszy
        int height = 4; // wysokość planszy
        int maxDepth = 20;// maksymalna głębokość dla DFS (można dostosować)
        int[][] readState;
        try {
            readState = Utils.readStateFromFile("4x4_przykładowa.txt");
        } catch (IOException e) {
            return;
        }
        // Wywołanie funkcji DFS
        System.out.println("Rozwiązywanie za pomocą DFS:");
        long startTime = System.nanoTime();
        List<String> resultDFS = Algorithms.solveDFS(startState, width, height, maxDepth);
        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1000000.0;
        System.out.println(resultDFS.isEmpty() ? "Brak rozwiązania!" : "Znaleziono rozwiązanie w " + resultDFS.size() + " ruchach: " + String.join(" ", resultDFS));
        System.out.println("Czas szukania rozwiazania: " + executionTime + "ms");
// Wywołanie funkcji BFS
        System.out.println("\nRozwiązywanie za pomocą BFS:");
        startTime = System.nanoTime();
        List<String> resultBFS = Algorithms.solveBFS(startState, width, height);
        endTime = System.nanoTime();
        executionTime = (endTime - startTime) / 1000000.0;
        System.out.println(resultBFS.isEmpty() ? "Brak rozwiązania!" : "Znaleziono rozwiązanie w " + resultBFS.size() + " ruchach: " + String.join(" ", resultBFS));
        System.out.println("Czas szukania rozwiazania: " + executionTime + "ms");
// Wywołanie funkcji Hamming
        System.out.println("\nRozwiązywanie za pomocą Hamming:");
        startTime = System.nanoTime();
        List<String> resultHamming = Heuristics.solveHeuristics(startState, width, height, true);
        endTime = System.nanoTime();
        executionTime = (endTime - startTime) / 1000000.0;
        System.out.println(resultHamming.isEmpty() ? "Brak rozwiązania!" : "Znaleziono rozwiązanie w " + resultHamming.size() + " ruchach: " + String.join(" ", resultHamming));
        System.out.println("Czas szukania rozwiazania: " + executionTime + "ms");
// Wywołanie funkcji Manhattan
        System.out.println("\nRozwiązywanie za pomocą Manhattan:");
        startTime = System.nanoTime();
        List<String> resultManhattan = Heuristics.solveHeuristics(startState, width, height, false);
        endTime = System.nanoTime();
        executionTime = (endTime - startTime) / 1000000.0;
        System.out.println(resultManhattan.isEmpty() ? "Brak rozwiązania!" : "Znaleziono rozwiązanie w " + resultManhattan.size() + " ruchach: " + String.join(" ", resultManhattan));
        System.out.println("Czas szukania rozwiazania: " + executionTime + "ms");
    }
}