public class EightQueens {

    static final int N = 8;

    // Utility function to print the board
    static void printBoard(int[] board) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i] == j)
                    System.out.print("Q ");
                else
                    System.out.print(". ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Check if placing a queen at row, col is safe
    static boolean isSafe(int[] board, int row, int col) {
        for (int i = 0; i < row; i++) {
            // Check same column
            if (board[i] == col) return false;
            // Check diagonals
            if (Math.abs(board[i] - col) == Math.abs(i - row)) return false;
        }
        return true;
    }

    // Recursive function to solve 8-Queens
    static boolean solveNQueens(int[] board, int row) {
        if (row == N) {
            // All queens placed
            printBoard(board);
            return true; // change to 'false' if you want all solutions
        }

        boolean foundSolution = false;
        for (int col = 0; col < N; col++) {
            if (isSafe(board, row, col)) {
                board[row] = col; // Place queen
                // Recursively place rest of queens
                foundSolution = solveNQueens(board, row + 1) || foundSolution;
                // Backtrack (not needed explicitly as we overwrite board[row])
            }
        }
        return foundSolution;
    }

    public static void main(String[] args) {
        int[] board = new int[N]; // board[i] = column of queen in row i
        if (!solveNQueens(board, 0)) {
            System.out.println("No solution exists");
        }
    }
}
