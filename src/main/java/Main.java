import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Niepoprawna liczba argumentów. Przykład: java Main <strategia> <porzadek_przeszukiwania> <plik_wejsciowy> <plik_rozwiazania> <plik_statystyk>");
            return;
        }

        String strategy = args[0];
        String order = args[1];
        String inputFile = args[2];
        String solutionFile = args[3];
        String statsFile = args[4];

        int[][] startState;
        int width = 4; // szerokość planszy
        int height = 4; // wysokość planszy
        int maxDepth = 20; // maksymalna głębokość dla DFS

        try {
            startState = Utils.readStateFromFile(inputFile);
        } catch (IOException e) {
            System.out.println("Błąd podczas wczytywania pliku wejściowego: " + e.getMessage());
            return;
        }

        List<String> result;
        long startTime, endTime;
        double executionTime;
        int visitedStates = 0, processedStates = 0, maxSearchDepth = 0;

        switch (strategy.toLowerCase()) {
            case "bfs":
                // BFS - wszerz
                System.out.println("Rozwiązywanie za pomocą BFS:");
                startTime = System.nanoTime();
                result = Algorithms.solveBFS(startState, width, height, order);
                endTime = System.nanoTime();
                executionTime = (endTime - startTime) / 1000000.0;
                break;

            case "dfs":
                // DFS - w głąb
                System.out.println("Rozwiązywanie za pomocą DFS:");
                startTime = System.nanoTime();
                result = Algorithms.solveDFS(startState, width, height, maxDepth, order);
                endTime = System.nanoTime();
                executionTime = (endTime - startTime) / 1000000.0;
                break;

            case "astr":
                // A* - strategia z heurystyką
                System.out.println("Rozwiązywanie za pomocą A*:");
                startTime = System.nanoTime();
                result = Heuristics.solveHeuristics(startState, width, height, order.equalsIgnoreCase("manh"));
                endTime = System.nanoTime();
                executionTime = (endTime - startTime) / 1000000.0;
                break;

            default:
                System.out.println("Nieznana strategia: " + strategy);
                return;
        }

        if (result.isEmpty()) {
            System.out.println("Brak rozwiązania!");
        } else {
            System.out.println("Znaleziono rozwiązanie w " + result.size() + " ruchach: " + String.join(" ", result));
        }

        System.out.println("Czas szukania rozwiązania: " + executionTime + "ms");

        // Zapis wyników do plików
        try {
            Utils.writeSolutionFile(solutionFile, result.size(), String.join(" ", result));
            Utils.writeAdditionalInfoFile(statsFile, result.size(), visitedStates, processedStates, maxSearchDepth, executionTime);
        } catch (IOException e) {
            System.out.println("Błąd podczas zapisywania wyników do pliku: " + e.getMessage());
        }
    }
}
