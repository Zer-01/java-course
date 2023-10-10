package edu.hw1;

public class Task8 {
    private Task8() {
    }

    private static final int BOARD_SIDE_SIZE = 8;
    private static final int POSS_MOVES_CNT = 8;

    public static boolean knightBoardCapture(int[][] board) {
        if (board == null) {
            throw new NullPointerException();
        }
        for (int x = 0; x < BOARD_SIDE_SIZE; x++) {
            for (int y = 0; y < BOARD_SIDE_SIZE; y++) {
                if (board[x][y] == 1 && isCapture(board, x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    @SuppressWarnings("MagicNumber")
    private static boolean isCapture(int[][] board, int x, int y) {
        int[][] moves = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
        int tmpX;
        int tmpY;
        for (int i = 0; i < POSS_MOVES_CNT; i++) {
            tmpX = x + moves[i][0];
            tmpY = y + moves[i][1];
            if (tmpX >= 0 && tmpX < BOARD_SIDE_SIZE && tmpY >= 0 && tmpY < BOARD_SIDE_SIZE && board[tmpX][tmpY] == 1) {
                return true;
            }
        }
        return false;
    }
}
