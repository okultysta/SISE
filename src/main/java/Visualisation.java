import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Visualisation {

    private static final int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};  // U, D, L, R
    private static final String[] names = {"U", "D", "L", "R"};

    public static void saveSolution(String filename, int[][] startState, List<String> path) throws IOException {
        List<int[][]> sequence = Visualisation.generateStateSequence(startState, path, moves, names);

        StringBuilder sb = new StringBuilder();

        sb.append("Length: ").append(path.size()).append("\n");
        sb.append("Moves: ").append(String.join(", ", path)).append("\n\n");

        int step = 0;
        for (int[][] state : sequence) {
            sb.append("Step ").append(step++).append(":\n");
            for (int[] row : state) {
                for (int value : row) {
                    sb.append(value).append("\t");
                }
                sb.append("\n");
            }
            sb.append("-----------\n");
        }



        Utils.writeSolutionFile(filename, sb.toString());
    }

    public static List<int[][]> generateStateSequence(int[][] startState, List<String> moves,
                                                      int[][] moveDirs, String[] moveNames) {
        List<int[][]> states = new ArrayList<>();
        int[][] currentState = deepCopy(startState);
        states.add(deepCopy(currentState)); // dodaj pierwszy stan

        int zeroX = -1, zeroY = -1;
        // znajdź pozycję zera w startState
        for (int i = 0; i < currentState.length; i++) {
            for (int j = 0; j < currentState[0].length; j++) {
                if (currentState[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                    break;
                }
            }
            if (zeroX != -1) break;
        }

        for (String move : moves) {
            // znajdź odpowiedni kierunek dla tego ruchu
            int dirIndex = -1;
            for (int i = 0; i < moveNames.length; i++) {
                if (moveNames[i].equals(move)) {
                    dirIndex = i;
                    break;
                }
            }
            if (dirIndex == -1) throw new IllegalArgumentException("Unknown move: " + move);

            int dx = moveDirs[dirIndex][0];
            int dy = moveDirs[dirIndex][1];
            int newX = zeroX + dx;
            int newY = zeroY + dy;

            // wykonaj ruch jeśli w granicach
            if (0 <= newX && newX < currentState.length && 0 <= newY && newY < currentState[0].length) {
                // zamiana
                int temp = currentState[zeroX][zeroY];
                currentState[zeroX][zeroY] = currentState[newX][newY];
                currentState[newX][newY] = temp;

                // aktualizacja pozycji zera
                zeroX = newX;
                zeroY = newY;

                // dodaj skopiowany stan do listy
                states.add(deepCopy(currentState));
            } else {
                throw new IllegalStateException("Invalid move: " + move);
            }
        }

        return states;
    }


    private static int[][] deepCopy(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }

}
