import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main {
    public static void main(String[] args) {
        // Inicjalizacja układanki 15 (rozmiar 4x4)
        List<List<Integer>> startState = new ArrayList<>();
        startState.add(Arrays.asList(5, 1, 3, 4));
        startState.add(Arrays.asList(9, 2, 6, 8));
        startState.add(Arrays.asList(13, 14, 7, 11));
        startState.add(Arrays.asList(0, 15, 10, 12));

        int width = 4; // szerokość planszy
        int height = 4; // wysokość planszy
        int maxDepth = 20; // maksymalna głębokość dla DFS (można dostosować)

        // Wywołanie funkcji DFS
        System.out.println("Rozwiązywanie za pomocą DFS:");
        List<String> resultDFS = PuzzleSolver.solveDFS(startState, width, height, maxDepth);
        System.out.println(resultDFS.isEmpty() ? "Brak rozwiązania!" : String.join(" ", resultDFS));

        // Wywołanie funkcji BFS
        System.out.println("\nRozwiązywanie za pomocą BFS:");
        List<String> resultBFS = PuzzleSolver.solveBFS(startState, width, height);
        System.out.println(resultBFS.isEmpty() ? "Brak rozwiązania!" : String.join(" ", resultBFS));


    }
}