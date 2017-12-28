package CSC242Project1;

public class NineBoard {
	Board[] boards;
	int alpha = Integer.MIN_VALUE;
	int beta = Integer.MAX_VALUE;
	
	//Initialize board blank
	public void initBoard() {
		boards = new Board[9];
		for (int i=0; i < boards.length; i++) {
			boards[i] = new Board();
			boards[i].initBoard();
		}
	}
	
	//Initialize board with another NineBoard
	public void initBoard2(NineBoard nineBoard) {
		boards = new Board[9];
		for (int i=0; i <boards.length; i++) {
			boards[i] = new Board();
			boards[i].initBoard2(nineBoard.boards[i]);
		}
	}
	
	//Print the nine-board
	public void printBoard() throws InterruptedException {
		//Thread.sleep(15);
		System.err.println("-----------------------");
		//three boards down  total (i)
		//0 1 2 boards first i, 3 4 5 boards second, 6 7 8 boards third
		for (int i=0; i < 3; i++) {
			//three rows each board (j)
			//0 1 2 position first i, 3 4 5 positions second, 6 7 8 positions third
			System.err.println("-----------------------");
			for (int j=0; j < 3; j++) {
				System.err.println("|" + boards[i*3].board[j*3] + "|" + boards[i*3].board[j*3+1] + "|" + boards[i*3].board[j*3+2] + "| |" + boards[i*3+1].board[j*3] + "|" + boards[i*3+1].board[j*3+1] + "|" + boards[i*3+1].board[j*3+2] + "| |" + boards[i*3+2].board[j*3] + "|" + boards[i*3+2].board[j*3+1] + "|" + boards[i*3+2].board[j*3+2] + "|");
			}
		}
		System.err.println("-----------------------");
		System.err.println("-----------------------");
		//Thread.sleep(15);
	}
	
	public String checkVictory() {
		for (int i=0; i < boards.length; i++) {
			if (boards[i].checkVictory() != null) {
				return boards[i].checkVictory();
			}
		}
		return null;
	}
	
	public boolean checkTie() {
		for (int i=0; i < boards.length; i++) {
			if (boards[i].checkTie()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkAllTie() {
		for (int i=0; i < boards.length; i++) {
			if (!boards[i].checkTie()) {
				return false;
			}
		}
		return true;
	}
	
	public NineBoard editEmptySpot(int posToEdit, String currentPlayer, int boardToEdit) {
		NineBoard newNBoard = new NineBoard();
		newNBoard.initBoard2(this);
		Board editedBoard = new Board();
		editedBoard.initBoard2(newNBoard.boards[boardToEdit].editEmptySpot(posToEdit, currentPlayer));
		newNBoard.boards[boardToEdit].initBoard2(editedBoard);
		return newNBoard;
	}

	public int numberEmptySpaces(int currentBoard) {
		return boards[currentBoard].numberEmptySpaces();
	}

	public int getEditEmptySpot(int posToEdit, int currentBoard) {
		return boards[currentBoard].getEditEmptySpot(posToEdit);
	}

	//check if current board is one away from winning
	public boolean oneAwayFromVictory(String aiChar, String playerChar) {
		//loop through the boards
		for (int i=0; i < boards.length; i++) {
			if (boards[i].oneAway(aiChar, playerChar)) {
				return true;
			}
		}
		return false;
	}
	
	//Check if current board is one away from losing
	public boolean oneAwayFromLosing(String aiChar, String playerChar) {
		if (oneAwayFromVictory(playerChar, aiChar)) {
			return true;
		}
		return false;
	}
	
	//returns how many boards 1 away from losing
	public int countOneAwayLosing(String aiChar, String playerChar) {
		int count = 0;
		for (int i=0; i < boards.length; i++) {
			if (boards[i].oneAway(playerChar, aiChar)) {
				count++;
			}
		}
		return count;
	}
	
	//returns how many boards 1 away from winning
	public int countOneAway(String aiChar, String playerChar) {
		int count = 0;
		for (int i=0; i < boards.length; i++) {
			if (boards[i].oneAway(aiChar, playerChar)) {
				count++;
			}
		}
		return count;
	}
}
