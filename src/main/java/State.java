import java.util.*;

class State {
    int[][] board;
    int zeroX, zeroY;
    List<String> path;

    State(int[][] b, int x, int y, List<String> p) {
        this.board = new int[b.length][b[0].length];
        for (int i = 0; i < b.length; i++) {
            System.arraycopy(b[i], 0, this.board[i], 0, b[i].length);
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
}
