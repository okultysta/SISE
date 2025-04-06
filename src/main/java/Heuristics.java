import java.io.IOException;
import java.util.*;

class Heuristics {

    static List<StateH> getNextStates(StateH state, int width, int height, int[][] goalBoard, boolean mode) {
        List<StateH> nextStates = new ArrayList<>();
        int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] move : moves) {
            int newX = state.zeroX + move[0];
            int newY = state.zeroY + move[1];

            if (newX >= 0 && newX < height && newY >= 0 && newY < width) {
                int[][] newBoard = new int[height][width];
                for (int i = 0; i < height; i++) {
                    newBoard[i] = Arrays.copyOf(state.board[i], width);
                }

                int temp = newBoard[state.zeroX][state.zeroY];
                newBoard[state.zeroX][state.zeroY] = newBoard[newX][newY];
                newBoard[newX][newY] = temp;

                List<String> newPath = new ArrayList<>(state.path);
                newPath.add(move[0] == -1 ? "U" : move[0] == 1 ? "D" : move[1] == -1 ? "L" : "R");

                StateH newState = new StateH(newBoard, newX, newY, newPath);
                newState.hValue = mode ? newState.calculateHamming(goalBoard) : newState.calculateManhattan(goalBoard);
                nextStates.add(newState);
            }
        }
        return nextStates;
    }

    static List<String> solveHeuristics(int[][] startState, int width, int height, boolean mode) {
        int visitedStates = 0;
        int maxDepth = 0;
        long startTime = System.nanoTime();
        int[][] goalBoard = new int[height][width];
        int number = 1;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                goalBoard[i][j] = number++;
            }
        }
        goalBoard[height - 1][width - 1] = 0;

        int zeroX = -1, zeroY = -1;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (startState[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                }
            }
        }

        PriorityQueue<StateH> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.hValue));
        StateH start = new StateH(startState, zeroX, zeroY, new ArrayList<>());
        start.hValue = mode ? start.calculateHamming(goalBoard) : start.calculateManhattan(goalBoard);
        pq.add(start);
        String whichHeuristic = "";
        if (mode) {
            whichHeuristic = "hamming";
        } else {
            whichHeuristic = "manhattan";
        }
        while (!pq.isEmpty()) {
            StateH currentState = pq.poll();

            visitedStates++;
            if (currentState.path.size() > maxDepth) {
                maxDepth = currentState.path.size();
            }

            if (Arrays.deepEquals(currentState.board, goalBoard)) {
                long endTime = System.nanoTime();
                double executionTime = (endTime - startTime) / 1000000.0;

                try {
                    Utils.writeSolutionFile("heurystyki" + whichHeuristic + "-rozwiazanie.txt", currentState.path.size(), String.valueOf(currentState.path));
                    Utils.writeAdditionalInfoFile("heurystyki" + whichHeuristic + "-dodatkowe-info.txt", currentState.path.size(), visitedStates, 0, maxDepth
                            , executionTime);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return currentState.path;
            }


            List<StateH> nextStates = getNextStates(currentState, width, height, goalBoard, mode);
            pq.addAll(nextStates);
        }
        try {
            Utils.writeSolutionFile("heurystyki" + whichHeuristic + "-rozwiazanie.txt", -1, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>();

    }
}

