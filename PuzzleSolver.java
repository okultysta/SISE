import java.util.*;

public class PuzzleSolver {
    private static final int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final String[] moveNames = {"U", "D", "L", "R"};

    private static List<State> getNextStates(State state, int width, int height) {
        List<State> nextStates = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int newX = state.zeroX + moves[i][0];
            int newY = state.zeroY + moves[i][1];

            if (newX >= 0 && newX < height && newY >= 0 && newY < width) {
                List<List<Integer>> newBoard = new ArrayList<>();
                for (List<Integer> row : state.board) {
                    newBoard.add(new ArrayList<>(row));
                }

                // Zamiana wartości w macierzy
                newBoard.get(state.zeroX).set(state.zeroY, newBoard.get(newX).get(newY));
                newBoard.get(newX).set(newY, 0);

                List<String> newPath = new ArrayList<>(state.path);
                newPath.add(moveNames[i]);

                nextStates.add(new State(newBoard, newX, newY, newPath));
            }
        }
        return nextStates;
    }

    private static List<List<Integer>> generateGoalBoard(int width, int height) {
        List<List<Integer>> goalBoard = new ArrayList<>();
        int number = 1;
        for (int i = 0; i < height; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(number++);
            }
            goalBoard.add(row);
        }
        goalBoard.get(height - 1).set(width - 1, 0);
        return goalBoard;
    }

    public static List<String> solveDFS(List<List<Integer>> startState, int width, int height, int maxDepth) {
        List<List<Integer>> goalBoard = generateGoalBoard(width, height);
        int zeroX = 0, zeroY = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (startState.get(i).get(j) == 0) {
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

            if (currentState.board.equals(goalBoard)) {
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

    public static List<String> solveBFS(List<List<Integer>> startState, int width, int height) {
        List<List<Integer>> goalBoard = generateGoalBoard(width, height);
        int zeroX = 0, zeroY = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (startState.get(i).get(j) == 0) {
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

            if (currentState.board.equals(goalBoard)) {
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


