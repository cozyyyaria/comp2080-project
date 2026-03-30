// ============================================================
// COMP 2080 - Data Structures and Algorithms
// Group Project - Winter 2026
// TicTacToe Game with Minimax AI
//
// Group Members:
// First Name: Ariana  Last Name: Cruz        Student ID: 101346000
// First Name: Ramtin  Last Name: Loghmani    Student ID: 101595929
// First Name: Arman   Last Name: Milani      Student ID: 
// First Name: Krishna Last Name: Venu        Student ID: 
// ============================================================

/**
 * AI
 * Implements the Minimax algorithm to determine the best possible
 * move for the computer player in a game of Tic-Tac-Toe.
 *
 * Minimax works by recursively simulating every possible future
 * game state. The AI assumes both players play optimally:
 *   - The AI (maximising player) tries to reach +10 (AI win)
 *   - The human (minimising player) tries to reach -10 (human win)
 *   - A draw scores 0
 *
 * Because the board is only 3x3, the search tree is small enough
 * that no depth limit or pruning is needed for correctness,
 * though the depth offset in the score rewards faster wins.
 */
public class AI {

    // Symbol assigned to the AI (e.g. 'O')
    private char aiSymbol;

    // Symbol assigned to the human player (e.g. 'X')
    private char humanSymbol;

    // --------------------------------------------------------
    // Constructor: store which symbol belongs to whom
    // --------------------------------------------------------
    public AI(char aiSymbol, char humanSymbol) {
        this.aiSymbol    = aiSymbol;
        this.humanSymbol = humanSymbol;
    }

    // --------------------------------------------------------
    // getBestMove
    // Iterates over every empty cell, runs Minimax on each,
    // and returns the [row, col] of the highest-scoring move.
    // --------------------------------------------------------
    public int[] getBestMove(TicTacToe game) {
        int bestScore = Integer.MIN_VALUE;
        int bestRow   = -1;
        int bestCol   = -1;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {

                // Only consider empty cells
                if (game.isCellEmpty(row, col)) {

                    // Try placing the AI's symbol here
                     

                    // Evaluate this move with Minimax (human moves next → minimising)
                    int score = minimax(game, 0, false);

                    // Undo the trial move
                     

                    // Keep track of the best scoring position
                    if (score > bestScore) {
                         
                    }
                }
            }
        }

        return new int[]{bestRow, bestCol};
    }

    // --------------------------------------------------------
    // minimax
    // Recursively scores a board state.
    //
    // isMaximising == true  → it is the AI's turn (wants high score)
    // isMaximising == false → it is the human's turn (wants low score)
    //
    // depth is used to favour quicker wins and slower losses:
    //   winning in fewer moves scores higher than winning later.
    // --------------------------------------------------------
    private int minimax(TicTacToe game, int depth, boolean isMaximising) {

        // --- Base cases: check if the game has already ended ---

        // AI has won this branch → return YOUR CODE HERE
        if (game.checkWin(aiSymbol)) {

        }

        // Human has won this branch → return YOUR CODE HERE
        if (game.checkWin(humanSymbol)) {

        }

        // No moves remain and no winner → draw → return YOUR CODE HERE
        if (game.isBoardFull()) {

        }

        // --- Recursive case ---

        if (isMaximising) {
            // AI's turn: look for the highest possible score
            int best = Integer.MIN_VALUE;

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (game.isCellEmpty(row, col)) {
                          // TODO: (make move, recurse, undo, update best)
                    }
                }
            }
            return best;

        } else {
            // Human's turn: look for the lowest possible score
            int best = Integer.MAX_VALUE;

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (game.isCellEmpty(row, col)) {
                          // TODO: (make move, recurse, undo, update best)
                    }
                }
            }
            return best;
        }
    }
}