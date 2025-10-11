import java.util.*;

public class TicTacToeMinimax {

    static final char PLAYER = 'X';   // AI or Maximizing player
    static final char HUMAN = 'O';    // Human or Minimizing player

    // Print the current board
    static void printBoard(char[][] board) {
        System.out.println("Board:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
    }

    // Check for a winner
    static int evaluate(char[][] board) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] == PLAYER) return +10;
                if (board[i][0] == HUMAN) return -10;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                if (board[0][i] == PLAYER) return +10;
                if (board[0][i] == HUMAN) return -10;
            }
        }
        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == PLAYER) return +10;
            if (board[0][0] == HUMAN) return -10;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == PLAYER) return +10;
            if (board[0][2] == HUMAN) return -10;
        }
        return 0; // No winner
    }

    // Check if moves are left
    static boolean isMovesLeft(char[][] board) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '_') return true;
        return false;
    }

    // Minimax algorithm
    static int minimax(char[][] board, int depth, boolean isMax) {
        int score = evaluate(board);

        // If AI wins
        if (score == 10) return score - depth;
        // If Human wins
        if (score == -10) return score + depth;
        // Draw
        if (!isMovesLeft(board)) return 0;

        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (board[i][j] == '_') {
                        board[i][j] = PLAYER;
                        best = Math.max(best, minimax(board, depth + 1, false));
                        board[i][j] = '_';
                    }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (board[i][j] == '_') {
                        board[i][j] = HUMAN;
                        best = Math.min(best, minimax(board, depth + 1, true));
                        board[i][j] = '_';
                    }
            return best;
        }
    }

    // Find the best move for AI
    static int[] findBestMove(char[][] board) {
        int bestVal = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '_') {
                    board[i][j] = PLAYER;
                    int moveVal = minimax(board, 0, false);
                    board[i][j] = '_';
                    if (moveVal > bestVal) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
        return bestMove;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[][] board = {
            {'_', '_', '_'},
            {'_', '_', '_'},
            {'_', '_', '_'}
        };

        System.out.println("Tic-Tac-Toe Game!");
        printBoard(board);

        while (true) {
            // Human move
            System.out.print("Enter your move (row and column: 0-2): ");
            int row = sc.nextInt();
            int col = sc.nextInt();
            if (board[row][col] != '_') {
                System.out.println("Invalid move! Try again.");
                continue;
            }
            board[row][col] = HUMAN;
            printBoard(board);

            if (evaluate(board) == -10) {
                System.out.println("You win!");
                break;
            }
            if (!isMovesLeft(board)) {
                System.out.println("Draw!");
                break;
            }

            // AI move
            int[] bestMove = findBestMove(board);
            board[bestMove[0]][bestMove[1]] = PLAYER;
            System.out.println("AI plays:");
            printBoard(board);

            if (evaluate(board) == 10) {
                System.out.println("AI wins!");
                break;
            }
            if (!isMovesLeft(board)) {
                System.out.println("Draw!");
                break;
            }
        }
        sc.close();
    }
}
