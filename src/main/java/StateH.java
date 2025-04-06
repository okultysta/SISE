import java.util.*;

class StateH {
    int[][] board;
    int zeroX, zeroY;
    List<String> path;
    int hValue = 0;

    StateH(int[][] b, int x, int y, List<String> p) {
        this.board = new int[b.length][b[0].length];
        for (int i = 0; i < b.length; i++) {
            this.board[i] = Arrays.copyOf(b[i], b[i].length);
        }
        this.zeroX = x;
        this.zeroY = y;
        this.path = new ArrayList<>(p);
    }

    String boardToString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : board) {
            for (int num : row) {
                sb.append(num).append(",");
            }
        }
        return sb.toString();
    }

    int calculateHamming(int[][] goalBoard) {
        int hamming = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != 0 && board[i][j] != goalBoard[i][j]) {
                    hamming++;
                }
            }
        }
        return hamming + path.size();
    }

    int calculateManhattan(int[][] goalBoard) {
        int distance = 0;
        int height = board.length;
        int width = board[0].length;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int value = board[i][j];
                if (value != 0) {
                    int goalX = (value - 1) / width;
                    int goalY = (value - 1) % width;
                    distance += Math.abs(i - goalX) + Math.abs(j - goalY);
                }
            }
        }
        return distance + path.size();
    }
}
