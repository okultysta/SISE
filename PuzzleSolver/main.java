package PuzzleSolver;
import java.util.List;

public class main {
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
        int maxDepth = 20; // maksymalna głębokość dla DFS (można dostosować)

        // Wywołanie funkcji DFS
        System.out.println("Rozwiązywanie za pomocą DFS:");
        List<String> resultDFS = Algorithms.solveDFS(startState, width, height, maxDepth);
        System.out.println(resultDFS.isEmpty() ? "Brak rozwiązania!" : "Znaleziono rozwiązanie w " + resultDFS.size() + " ruchach: " + String.join(" ", resultDFS));

// Wywołanie funkcji BFS
        System.out.println("\nRozwiązywanie za pomocą BFS:");
        List<String> resultBFS = Algorithms.solveBFS(startState, width, height);
        System.out.println(resultBFS.isEmpty() ? "Brak rozwiązania!" : "Znaleziono rozwiązanie w " + resultBFS.size() + " ruchach: " + String.join(" ", resultBFS));

// Wywołanie funkcji Hamming
        System.out.println("\nRozwiązywanie za pomocą Hamming:");
        List<String> resultHamming = Heuristics.solveHeuristics(startState, width, height, maxDepth, true);
        System.out.println(resultHamming.isEmpty() ? "Brak rozwiązania!" : "Znaleziono rozwiązanie w " + resultHamming.size() + " ruchach: " + String.join(" ", resultHamming));

// Wywołanie funkcji Manhattan
        System.out.println("\nRozwiązywanie za pomocą Manhattan:");
        List<String> resultManhattan = Heuristics.solveHeuristics(startState, width, height, maxDepth, false);
        System.out.println(resultManhattan.isEmpty() ? "Brak rozwiązania!" : "Znaleziono rozwiązanie w " + resultManhattan.size() + " ruchach: " + String.join(" ", resultManhattan));
    }
}