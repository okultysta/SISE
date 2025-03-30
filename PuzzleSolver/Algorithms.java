package PuzzleSolver;

import java.util.*;

public class Algorithms {
    private static final int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final String[] moveNames = {"U", "D", "L", "R"};

    private static List<State> getNextStates(State state, int width, int height) {
        List<State> nextStates = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
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

    public static List<String> solveDFS(int[][] startState, int width, int height, int maxDepth) {
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

        Stack<State> stack = new Stack<>();
        State start = new State(startState, zeroX, zeroY, new ArrayList<>());
        stack.push(start);

        while (!stack.isEmpty()) {
            State currentState = stack.pop();

            if (Arrays.deepEquals(currentState.board, goalBoard)) {
                return currentState.path;
            }

            if (currentState.path.size() >= maxDepth) {
                continue;
            }

            for (State nextState : getNextStates(currentState, width, height)) {
                stack.push(nextState);
            }
        }

        return new ArrayList<>();
    }

    public static List<String> solveBFS(int[][] startState, int width, int height) {
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

        while (!queue.isEmpty()) {
            State currentState = queue.poll();

            if (Arrays.deepEquals(currentState.board, goalBoard)) {
                return currentState.path;
            }

            for (State nextState : getNextStates(currentState, width, height)) {
                if (!visited.contains(nextState.boardToString())) {
                    visited.add(nextState.boardToString());
                    queue.offer(nextState);
                }
            }
        }

        return new ArrayList<>();
    }
}