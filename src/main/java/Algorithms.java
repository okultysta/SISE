import java.io.IOException;
import java.util.*;

public class Algorithms {
    private static final int[][] defaultMoves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};  // U, D, L, R
    private static final String[] defaultMoveNames = {"U", "D", "L", "R"};

    private static List<State> getNextStates(State state, int width, int height, int[][] moves, String[] moveNames) {
        List<State> nextStates = new ArrayList<>();
        for (int i = 0; i < moves.length; i++) {
            int newX = state.zeroX + moves[i][0];
            int newY = state.zeroY + moves[i][1];

            if (newX >= 0 && newX < height && newY >= 0 && newY < width) {
                int[][] newBoard = new int[height][width];
                for (int r = 0; r < height; r++) {
                    newBoard[r] = state.board[r].clone();
                }

                // Zamiana wartości w macierzy
                newBoard[state.zeroX][state.zeroY] = newBoard[newX][newY];
                newBoard[newX][newY] = 0;

                List<String> newPath = new ArrayList<>(state.path);
                newPath.add(moveNames[i]);

                nextStates.add(new State(newBoard, newX, newY, newPath));
            }
        }
        return nextStates;
    }

    private static int[][] generateGoalBoard(int width, int height) {
        int[][] goalBoard = new int[height][width];
        int number = 1;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                goalBoard[i][j] = number++;
            }
        }
        goalBoard[height - 1][width - 1] = 0;
        return goalBoard;
    }

    public static List<String> solveDFS(int[][] startState, int width, int height, int maxDepth, String moveOrder, String filename, String infoFileName) {
        long startTime = System.nanoTime();
        int[][] goalBoard = generateGoalBoard(width, height);
        int zeroX = 0, zeroY = 0;
        int procesedStates = 0;

        // Znajdowanie pozycji 0
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (startState[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                }
            }
        }

        Stack<State> stack = new Stack<>();
        Set<String> visited = new HashSet<>();
        State start = new State(startState, zeroX, zeroY, new ArrayList<>());
        stack.push(start);
        visited.add(start.boardToString());

        int[][] moves = (int[][]) getMoveOrder(moveOrder)[0];
        String[] moveNames = (String[]) getMoveOrder(moveOrder)[1];

        while (!stack.isEmpty()) {
            State currentState = stack.pop();

            // Sprawdzenie, czy stan jest celem
            if (Arrays.deepEquals(currentState.board, goalBoard)) {
                long endTime = System.nanoTime();
                double executionTime = (endTime - startTime) / 1000000.0;
                try {
                    Utils.writeSolutionFile(filename, currentState.path.size(), String.valueOf(currentState.path));
                    Utils.writeAdditionalInfoFile(infoFileName, currentState.path.size(), visited.size(), procesedStates, maxDepth, executionTime);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return currentState.path;
            }

            // Jeśli przekroczył max głębokość, pomijamy stan
            if (currentState.path.size() >= maxDepth) {
                continue;
            }

            // Dodanie nowych stanów do stosu
            for (State nextState : getNextStates(currentState, width, height, moves, moveNames)) {
                procesedStates++;
                if (!visited.contains(nextState.boardToString())) {
                    visited.add(nextState.boardToString());
                    stack.push(nextState);
                }
            }
        }
        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1000000.0;
        try {
            Utils.writeSolutionFile(filename, -1, "");
            Utils.writeAdditionalInfoFile(infoFileName, -1, visited.size(), procesedStates, maxDepth, executionTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();  // Brak rozwiązania
    }

    public static List<String> solveBFS(int[][] startState, int width, int height, String moveOrder,  String filename, String infoFileName) {
        long startTime = System.nanoTime();
        int maxDepth = 0;
        int procesedStates = 0;
        int[][] goalBoard = generateGoalBoard(width, height);
        int zeroX = 0, zeroY = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (startState[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                }
            }
        }

        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        State start = new State(startState, zeroX, zeroY, new ArrayList<>());
        queue.offer(start);
        visited.add(start.boardToString());

        // Dynamicznie zmieniamy porządek przeszukiwania
        int[][] moves = (int[][]) getMoveOrder(moveOrder)[0];
        String[] moveNames = (String[]) getMoveOrder(moveOrder)[1];

        while (!queue.isEmpty()) {
            State currentState = queue.poll();

            // Zaktualizowanie max głębokości
            if (currentState.path.size() > maxDepth) {
                maxDepth = currentState.path.size();
            }

            // Sprawdzamy, czy stan jest celem
            if (Arrays.deepEquals(currentState.board, goalBoard)) {
                long endTime = System.nanoTime();
                double executionTime = (endTime - startTime) / 1000000.0;
                try {
                    Utils.writeSolutionFile(filename, currentState.path.size(), String.valueOf(currentState.path));
                    Utils.writeAdditionalInfoFile(infoFileName, currentState.path.size(), visited.size(), procesedStates, maxDepth, executionTime);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return currentState.path;
            }

            // Dodanie nowych stanów do kolejki
            for (State nextState : getNextStates(currentState, width, height, moves, moveNames)) {
                procesedStates++;
                if (!visited.contains(nextState.boardToString())) {
                    visited.add(nextState.boardToString());
                    queue.offer(nextState);
                }
            }
        }
        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1000000.0;
        try {
            Utils.writeSolutionFile(filename, -1, "");
            Utils.writeAdditionalInfoFile(infoFileName, -1, visited.size(), procesedStates, maxDepth, executionTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();  // Brak rozwiązania
    }

    private static Object[] getMoveOrder(String moveOrder) {
        Map<Character, int[]> directionMap = new HashMap<>();
        directionMap.put('U', new int[]{-1, 0});
        directionMap.put('D', new int[]{1, 0});
        directionMap.put('L', new int[]{0, -1});
        directionMap.put('R', new int[]{0, 1});

        if (moveOrder.length() != 4 || !moveOrder.matches("[UDLR]{4}")) {
            throw new IllegalArgumentException("Niepoprawny porządek przeszukiwania: " + moveOrder);
        }

        int[][] moveVectors = new int[4][2];
        String[] moveNames = new String[4];

        for (int i = 0; i < 4; i++) {
            char dir = moveOrder.charAt(i);
            moveVectors[i] = directionMap.get(dir);
            moveNames[i] = String.valueOf(dir);
        }

        return new Object[]{moveVectors, moveNames};
    }


}
