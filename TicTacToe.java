// ============================================================
// COMP 2080 - Data Structures and Algorithms
// Group Project - Winter 2026
// TicTacToe Game with Minimax AI
//
// Group Members:
// First Name: Ariana  Last Name: Cruz        Student ID: 101346000
// First Name: Ramtin  Last Name: Loghmani    Student ID: 101595929
// First Name: Arman   Last Name: Milani      Student ID: 101555606
// First Name: Krishna Last Name: Venu        Student ID: 101484996
// ============================================================

import java.util.Scanner;

/**
 * TicTacToe
 * Manages the 3x3 board state, display, move validation,
 * and win/draw detection. Uses a basic char[][] array only.
 */
public class TicTacToe {

    // The board: 3x3 grid of characters. '.' = empty cell.
    private char[][] board;

    // Scanner shared across the whole game
    private Scanner scanner;

    // --------------------------------------------------------
    // Constructor: initialise an empty board
    // --------------------------------------------------------
    public TicTacToe() {
        board = new char[3][3];
        scanner = new Scanner(System.in);
        initBoard();
    }

    // --------------------------------------------------------
    // Fill every cell with '.' to represent an empty square
    // --------------------------------------------------------
    public void initBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = '.';
            }
        }
    }

    // --------------------------------------------------------
    // Pretty-print the board with row/column guides
    // --------------------------------------------------------
    public void displayBoard() {
        System.out.println();
        System.out.println("       Col 1  Col 2  Col 3");
        System.out.println("      +------+------+------+");
        for (int row = 0; row < 3; row++) {
            System.out.print("Row " + (row + 1) + " |");
            for (int col = 0; col < 3; col++) {
                System.out.print("  " + board[row][col] + "   |");
            }
            System.out.println();
            System.out.println("      +------+------+------+");
        }
        System.out.println();
    }

    // --------------------------------------------------------
    // Place a symbol on the board (assumes move is valid)
    // --------------------------------------------------------
    public void makeMove(int row, int col, char symbol) {
        board[row][col] = symbol;
    }

    // --------------------------------------------------------
    // Undo a move (used by the AI during search)
    // --------------------------------------------------------
    public void undoMove(int row, int col) {
        board[row][col] = '.';
    }

    // --------------------------------------------------------
    // Returns true if the cell at (row, col) is empty
    // --------------------------------------------------------
    public boolean isCellEmpty(int row, int col) {
        return board[row][col] == '.';
    }

    // --------------------------------------------------------
    // Returns true if the given symbol has won
    // Checks all rows, columns, and both diagonals
    // --------------------------------------------------------
    public boolean checkWin(char symbol) {
        // Check all three rows
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == symbol &&
                board[row][1] == symbol &&
                board[row][2] == symbol) {
                return true;
            }
        }

        // Check all three columns
        for (int col = 0; col < 3; col++) {
            if (board[0][col] == symbol &&
                board[1][col] == symbol &&
                board[2][col] == symbol) {
                return true;
            }
        }

        // Check top-left to bottom-right diagonal
        if (board[0][0] == symbol &&
            board[1][1] == symbol &&
            board[2][2] == symbol) {
            return true;
        }

        // Check top-right to bottom-left diagonal
        if (board[0][2] == symbol &&
            board[1][1] == symbol &&
            board[2][0] == symbol) {
            return true;
        }

        return false;
    }

    // --------------------------------------------------------
    // Returns true if every cell is filled (no moves remain)
    // --------------------------------------------------------
    public boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == '.') {
                    return false;
                }
            }
        }
        return true;
    }

    // --------------------------------------------------------
    // Returns true when the game is over (win or draw)
    // --------------------------------------------------------
    public boolean isGameOver(char playerSymbol, char aiSymbol) {
        return checkWin(playerSymbol) || checkWin(aiSymbol) || isBoardFull();
    }

    // --------------------------------------------------------
    // Ask the human player for their move.
    // Re-prompts on invalid input or occupied cell.
    // Row/column are 1-indexed in the UI, 0-indexed internally.
    // --------------------------------------------------------
    public int[] getHumanMove(String playerName) {
        int row = -1;
        int col = -1;

        while (true) {
            System.out.print(playerName + ", enter row (1-3): ");
            if (scanner.hasNextInt()) {
                row = scanner.nextInt() - 1;   // convert to 0-based
            } else {
                System.out.println("  Invalid input. Please enter a number.");
                scanner.next();  // discard bad token
                continue;
            }

            System.out.print(playerName + ", enter column (1-3): ");
            if (scanner.hasNextInt()) {
                col = scanner.nextInt() - 1;
            } else {
                System.out.println("  Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }

            // Boundary check
            if (row < 0 || row > 2 || col < 0 || col > 2) {
                System.out.println("  Out of range. Row and column must each be 1, 2, or 3.");
                continue;
            }

            // Occupancy check
            if (!isCellEmpty(row, col)) {
                System.out.println("  That cell is already taken. Choose another.");
                continue;
            }

            break;  // valid move found
        }

        return new int[]{row, col};
    }

    // --------------------------------------------------------
    // Expose the board array to the AI class for evaluation
    // --------------------------------------------------------
    public char[][] getBoard() {
        return board;
    }

    // --------------------------------------------------------
    // Get the shared Scanner (used by the main game loop)
    // --------------------------------------------------------
    public Scanner getScanner() {
        return scanner;
    }

    // --------------------------------------------------------
    // Run a 2-player (Human vs Human) game
    // --------------------------------------------------------
    public void playTwoPlayer() {
        System.out.println("\n=== TWO PLAYER MODE ===\n");

        // Collect player names and symbols
        System.out.print("Player 1, enter your name: ");
        String p1Name = scanner.next();

        char p1Symbol = ' ';
        while (p1Symbol != 'X' && p1Symbol != 'O') {
            System.out.print(p1Name + ", choose your symbol (X or O): ");
            String input = scanner.next().toUpperCase();
            if (input.equals("X") || input.equals("O")) {
                p1Symbol = input.charAt(0);
            } else {
                System.out.println("  Please enter X or O.");
            }
        }

        System.out.print("Player 2, enter your name: ");
        String p2Name = scanner.next();

        // Player 2 automatically gets the other symbol
        char p2Symbol = (p1Symbol == 'X') ? 'O' : 'X';
        System.out.println(p2Name + " will play as " + p2Symbol + ".");

        // X always goes first
        String currentName   = (p1Symbol == 'X') ? p1Name   : p2Name;
        char   currentSymbol = 'X';
        String otherName     = (p1Symbol == 'X') ? p2Name   : p1Name;
        char   otherSymbol   = 'O';

        System.out.println("\n" + currentName + " (" + currentSymbol + ") goes first.\n");
        displayBoard();

        // Main game loop
        while (true) {
            // Get and apply the current player's move
            int[] move = getHumanMove(currentName);
            makeMove(move[0], move[1], currentSymbol);
            displayBoard();

            // Check for win
            if (checkWin(currentSymbol)) {
                System.out.println("*** " + currentName + " (" + currentSymbol + ") wins! Congratulations! ***\n");
                break;
            }

            // Check for draw
            if (isBoardFull()) {
                System.out.println("*** It's a draw! Well played by both! ***\n");
                break;
            }

            // Swap players using temp variables
            String tempName   = currentName;
            char   tempSymbol = currentSymbol;
            currentName   = otherName;
            currentSymbol = otherSymbol;
            otherName     = tempName;
            otherSymbol   = tempSymbol;
        }
    }

    // --------------------------------------------------------
    // Run a 1-player (Human vs Minimax AI) game
    // --------------------------------------------------------
    public void playOnePlayer() {
        System.out.println("\n=== ONE PLAYER MODE (vs AI) ===\n");

        System.out.print("Enter your name: ");
        String humanName = scanner.next();

        char humanSymbol = ' ';
        while (humanSymbol != 'X' && humanSymbol != 'O') {
            System.out.print(humanName + ", choose your symbol (X or O): ");
            String input = scanner.next().toUpperCase();
            if (input.equals("X") || input.equals("O")) {
                humanSymbol = input.charAt(0);
            } else {
                System.out.println("  Please enter X or O.");
            }
        }

        char aiSymbol = (humanSymbol == 'X') ? 'O' : 'X';
        System.out.println("The AI will play as " + aiSymbol + ".\n");

        // Create the AI, telling it which symbol it uses and which the human uses
        AI ai = new AI(aiSymbol, humanSymbol);

        // X goes first regardless of who has it
        boolean isHumanTurn = (humanSymbol == 'X');
        System.out.println((isHumanTurn ? humanName : "The AI") + " (" + 'X' + ") goes first.\n");
        displayBoard();

        while (true) {
            if (isHumanTurn) {
                // --- Human's turn ---
                int[] move = getHumanMove(humanName);
                makeMove(move[0], move[1], humanSymbol);
                displayBoard();

                if (checkWin(humanSymbol)) {
                    System.out.println("*** " + humanName + " wins! Impressive! ***\n");
                    break;
                }
            } else {
                // --- AI's turn ---
                System.out.println("The AI is thinking...");
                int[] aiMove = ai.getBestMove(this);
                makeMove(aiMove[0], aiMove[1], aiSymbol);
                System.out.println("The AI placed " + aiSymbol +
                                   " at Row " + (aiMove[0] + 1) +
                                   ", Col " + (aiMove[1] + 1) + ".");
                displayBoard();

                if (checkWin(aiSymbol)) {
                    System.out.println("*** The AI wins! Better luck next time, " + humanName + ". ***\n");
                    break;
                }
            }

            if (isBoardFull()) {
                System.out.println("*** It's a draw! Great game, " + humanName + ". ***\n");
                break;
            }

            // Swap turns
            isHumanTurn = !isHumanTurn;
        }
    }

    // --------------------------------------------------------
    // Entry point: ask for game mode, run accordingly
    // --------------------------------------------------------
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        Scanner sc = game.getScanner();

        System.out.println("==============================");
        System.out.println("   Welcome to Tic-Tac-Toe!   ");
        System.out.println("==============================");

        int mode = 0;
        while (mode != 1 && mode != 2) {
            System.out.println("\nSelect game mode:");
            System.out.println("  1 - Two Player (Human vs Human)");
            System.out.println("  2 - One Player (Human vs AI)");
            System.out.print("Enter 1 or 2: ");

            if (sc.hasNextInt()) {
                mode = sc.nextInt();
                if (mode != 1 && mode != 2) {
                    System.out.println("  Please enter 1 or 2.");
                }
            } else {
                System.out.println("  Please enter 1 or 2.");
                sc.next();
            }
        }

        if (mode == 1) {
            game.playTwoPlayer();
        } else {
            game.playOnePlayer();
        }
    }
}
