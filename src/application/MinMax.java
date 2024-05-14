package application;

public class MinMax {

	public static char computer = 'o';
	public static char human = 'x';

	// Checks if there are available moves on the board
	static Boolean hasMoves(char board[][]) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (board[i][j] == ' ')
					return true;
		return false;
	}

	// Evaluates current state on board for win conditions
	static int wining(char board[][]) {
		int[][] win = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, { 0, 4, 8 },
				{ 2, 4, 6 } };

		for (int i = 0; i < win.length; i++) {
			int[] n1 = coordinates(win[i][0]);
			int[] n2 = coordinates(win[i][1]);
			int[] n3 = coordinates(win[i][2]);
			if (board[n1[0]][n1[1]] != ' ' && board[n1[0]][n1[1]] == board[n2[0]][n2[1]]
					&& board[n2[0]][n2[1]] == board[n3[0]][n3[1]]) {
				if (board[n1[0]][n1[1]] == computer)
					return 1;
				else
					return -1;
			}
		}

		return 0;
	}

	
	// Converts a linear index to a 2D array index
	private static int[] coordinates(int n) {
		int x = n % 3;
		int y = n / 3;

		int[] arr = { x, y };
		return arr;
	}

	// Minmax method
	static int minmax(char board[][], Boolean isCompMove) {
		// Check if the current state is a win
		int score = wining(board);
		// If a win return score
		if (score != 0)
			return score;
		// If there are no more moves return 0
		if (hasMoves(board) == false)
			return 0;
		// If it's computer turn find the maximum score
		if (isCompMove) {
			int best = -Integer.MAX_VALUE;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					// Check empty cell
					if (board[i][j] == ' ') {
						// Make a move for computer
						board[i][j] = computer;

						// Call minmax for the next state
						best = Math.max(best, minmax(board, false));

						// Undo the move for backtracking
						board[i][j] = ' ';
					}
				}
			}

			return best;
		} else {
			// If it's human turn find minimum score
			int best = Integer.MAX_VALUE;

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					// Check empty cell
					if (board[i][j] == ' ') {
						// Make a move for human
						board[i][j] = human;

						// Call minmax for the next state
						best = Math.min(best, minmax(board, true));

						// Undo the move for backtracking
						board[i][j] = ' ';
					}
				}
			}
			return best;
		}
	}

	// Find the best move for the computer
	public static Move getBestMove(char board[][]) {
		int bestVal = -Integer.MAX_VALUE;
		Move bestMove = new Move();
		bestMove.row = -1;
		bestMove.col = -1;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {

				// Check if the cell is empty
				if (board[i][j] == ' ') {
					// Make a move for the computer player
					board[i][j] = computer;

					// Evaluate the move using the Minimax algorithm
					int moveVal = minmax(board, false);

					// Undo the move for backtracking
					board[i][j] = ' ';

					// Update if the evaluated value is better than the current best
					if (moveVal > bestVal) {
						bestMove.row = i;
						bestMove.col = j;
						bestVal = moveVal;
					}
				}
			}
		}

		return bestMove;
	}

}
