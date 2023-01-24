package ConnectFour;
import java.util.*;
class Game {
    private int[][] board; // 2D array to store the state of the board
    private int rows; // Number of rows
    private int cols; // Number of columns
    private int numToWin; // Number of consecutive pieces needed to win
    private int currentPlayer; // Player whose turn it is

    // constructor to initialize the board
    public Game(int rows, int cols, int numToWin) {
        this.rows = rows;
        this.cols = cols;
        this.numToWin = numToWin;
        board = new int[rows][cols];
		this.initBoard();
        currentPlayer = 1; // player 1 starts the game
    }
	
	public Game(int numToWin) {
        this(6, 7, numToWin);
    }
	
	public Game() {
        this(6, 7, 4);
    }

    // methods to play the game
    public void initBoard() {
		for	(int row = 0; row < this.rows; row++) {
			for (int col = 0; col < this.cols; col++) {
				this.board[row][col] = 0;
			}
		}
	}
	
	public boolean makeMove(int col) {
	   int row;
	   if (col < 0 || this.cols <= col) {
		   System.out.println("Column doesn't exist");
			return false;
	   }
	   if (this.board[this.rows - 1][col] != 0) {
		   System.out.println("Column is full");
		   return false;
	   }
	   for (row = 0; this.board[row][col] != 0; row++); // Choose the lowest empty tile
	   this.board[row][col] = currentPlayer;
	   if (this.checkWin(row, col)) {
		   System.out.println(String.format("Player %d wins!", currentPlayer));
		   currentPlayer = 0; // Stop the game
		   return true;
	   }
	   else if (this.checkTie()) {
		   System.out.println("Tie!");
		   currentPlayer = 0; // Stop the game
	   }
	   else {
		   currentPlayer = 3 - currentPlayer; // Pass the turn
	   }
	   return true;
    }
	
    public boolean checkWin(int lastMoveRow, int lastMoveCol) {
        int row, col = lastMoveCol, max = 0, cur = 0;
		// Check column
		for	(int i = - this.numToWin + 1; i <= 0; i++) {
			row = lastMoveRow + i;
			if (row < 0 || this.rows <= row) {
				continue;
			}
			if (this.board[row][col] == this.currentPlayer) {
				cur++;
			}
			else {
				cur = 0;
			}
		}
		if (cur == numToWin) {
			return true;
		}
		// Check row
		row = lastMoveRow;
		cur = 0;
		for	(int i = - this.numToWin + 1; i <= this.numToWin - 1; i++) {
			col = lastMoveCol + i;
			if (col < 0 || this.cols <= col) {
				continue;
			}
			if (this.board[row][col] == this.currentPlayer) {
				cur++;
				if (cur == numToWin) {
					return true;
				}
			}
			else {
				cur = 0;
			}
		}
		if (cur == numToWin) {
			return true;
		}
		// Check first diagonal
		cur = 0;
		for	(int i = - this.numToWin + 1; i <= this.numToWin - 1; i++) {
			row = lastMoveRow + i;
			col = lastMoveCol + i;
			if (row < 0 || this.rows <= row || col < 0 || this.cols <= col) {
				continue;
			}
			if (this.board[row][col] == this.currentPlayer) {
				cur++;
				if (cur == numToWin) {
					return true;
				}
			}
			else {
				cur = 0;
			}
		}
		if (cur == numToWin) {
			return true;
		}
		// Check second diagonal
		cur = 0;
		for	(int i = - this.numToWin + 1; i <= this.numToWin - 1; i++) {
			row = lastMoveRow + i;
			col = lastMoveCol - i;
			if (row < 0 || this.rows <= row || col < 0 || this.cols <= col) {
				continue;
			}
			if (this.board[row][col] == this.currentPlayer) {
				cur++;
				if (cur == numToWin) {
					return true;
				}
			}
			else {
				cur = 0;
			}
		}
		if (cur == numToWin) {
			return true;
		}
		return false;
    }
	
    public boolean checkTie() {
		int count = 0;
        for (int row = 0; row < this.rows; row++) {
			for (int col = 0; col < this.cols; col++) {
				if (this.board[row][col] != 0) {
					count++;
				}
			}
		}
		return count == (this.rows * this.cols);
    }

    public void printBoard() {
		for (int row = this.rows - 1; 0 <= row; row--) {
			System.out.print("\033[48;5;230m");
			for (int col = 0; col < this.cols; col++) {
				if (this.board[row][col] == 0) {
					System.out.print("- ");
				} else if (this.board[row][col] == 1) {
					System.out.print("\033[30mO\033[39m ");
				} else {
					System.out.print("\033[31mO\033[39m ");
				}
			}
			System.out.println("\033[49m");
		}
		System.out.print("\033[0m");
	}
	
	public void run() {
		this.initBoard();
		Scanner sc = new Scanner(System.in);
		while (currentPlayer != 0) {
			this.printBoard();
			System.out.println(String.format("Player %d's turn", this.currentPlayer));
			System.out.println("Enter column number");
			if (!this.makeMove(sc.nextInt() - 1)) {
				continue;
			}
			if (this.currentPlayer == 0) {
				this.printBoard();
				System.out.println("Game over");
				break;
			}
		}
	}
}
