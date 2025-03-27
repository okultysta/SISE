import java.util.*;

class State {
    List<List<Integer>> board;
    int zeroX, zeroY;
    List<String> path;

    State(List<List<Integer>> b, int x, int y, List<String> p) {
        this.board = new ArrayList<>();
        for (List<Integer> row : b) {
            this.board.add(new ArrayList<>(row));
        }
        this.zeroX = x;
        this.zeroY = y;
        this.path = new ArrayList<>(p);
    }

    String boardToString() {
        StringBuilder sb = new StringBuilder();
        for (List<Integer> row : board) {
            for (int num : row) {
                sb.append(num).append(",");
            }
        }
        return sb.toString();
    }
}

