package CSC242Project1;

public class Board {
	String board[];
	int alpha = Integer.MIN_VALUE;
	int beta = Integer.MAX_VALUE;
	
	//Sets the board to all ' '
	public void initBoard() {
		board = new String[9];
		for (int i=0; i < board.length; i++) {
			board[i] = String.valueOf(i+1);
		}
	}
	
	//Initialize a board
	public void initBoard2(Board board2) {
		board = new String[9];
		for (int i=0; i < board.length; i++) {
			board[i] = board2.board[i];
		}
		alpha = board2.alpha;
		beta = board2.beta;
	}
	
	/* TEST CODE */
	//For hardcoding the board to start a certain way
	public void testBoard(String[] spots) {
		for (int i=0; i < board.length; i++) {
			board[i] = spots[i];
		}
	}
	
	public boolean checkTie() {
		//check all board positions
		for (int i=0; i < board.length; i++) {
			//if there is a position that does not equal X nor O, return false
			if (!board[i].equals("X") && !board[i].equals("O")) {
				return false;
			}
		}
		
		//otherwise return true
		return true;
	}
	
	public int numberEmptySpaces() {
		int emptySpots = 0;
		for (int i=0; i < board.length; i++) {
			if (!board[i].equals("X") && !board[i].equals("O")) {
				emptySpots++;
			}
		}
		return emptySpots;
	}
	
	public String checkVictory() {
		//check top 3
		if (board[0].equals(board[1]) && board[1].equals(board[2])) {
			return board[0];
		//check left side 3
		} else if (board[0].equals(board[3]) && board[3].equals(board[6])) {
			return board[0];
		//check diagonal left top
		} else if (board[0].equals(board[4]) && board[4].equals(board[8])) {
			return board[0];
		//check bottom 3
		} else if (board[8].equals(board[7]) && board[7].equals(board[6])) {
			return board[6];
		}
		//check right side 3
		else if (board[8].equals(board[5]) && board[5].equals(board[2])){
			return board[8];
		}
		//check diagonal left bottom
		else if (board[6].equals(board[4]) && board[4].equals(board[2])) {
			return board[2];
		}
		//check middle three down
		else if (board[1].equals(board[4]) && board[4].equals(board[7])) {
			return board[1];
		}
		//check middle three across
		else if (board[3].equals(board[4]) && board[4].equals(board[5])) {
			return board[3];
		}
		
		return null;
	}
	
	public void printBoard() throws InterruptedException {
		//Thread.sleep(15);
		System.err.println("-------");
		for (int i=0; i<3; i++) {
			//TESTING CODE BELOW: Prints slightly differently:
			/*System.out.print("|");
			if (board[0 + i*3].equals("X") || board[0+i*3].equals("O")) {
				System.out.print(board[0 + i*3]);
			}
			System.out.print("|");
			if (board[1 + i*3].equals("X") || board[1+i*3].equals("O")) {
				System.out.print(board[1 + i*3]);
			}
			System.out.print("|");
			if (board[2 + i*3].equals("X") || board[2+i*3].equals("O")) {
				System.out.print(board[2 + i*3]);
			}*/
			//System.out.print("|");
			//Testing code above, prints slightly differently
			
			//Normal print:
			System.err.println("|" + board[0 + i*3] + "|" + board[1 + i*3] + "|" + board[2 + i*3] + "|");
		}
		System.err.println("-------");
		//Thread.sleep(15);
	}
	
	//Edit the next empty spot (x) spots deep in the board
	public Board editEmptySpot(int posToEdit, String currentPlayer) {
		Board newBoard = new Board();
		newBoard.initBoard2(this);
		
		//Loop all board positions and if X or O do nothing, otherwise insert the current player at position or
		//decrease posToEdit to go to next editable spot
		for (int i=0; i < newBoard.board.length; i++) {
			if (newBoard.board[i].equals("X") || newBoard.board[i].equals("O")) {
				//do nothing, it is not the next empty spot
			} else {
				if (posToEdit == 0) {
					newBoard.board[i] = currentPlayer;
					break;
				} else {
					posToEdit--;
				}
			}
		}
		
		
		return newBoard;
	}

	//gets the number of the next empty spot
	public int getEditEmptySpot(int posToEdit) {
		for (int i=0; i < board.length; i++) {
			if (board[i].equals("X") || board[i].equals("O")) {
				//do nothing, it is not the next empty spot
			} else {
				if (posToEdit == 0) {
					return (Integer.parseInt(board[i])-1);
				} else {
					posToEdit--;
				}
			}
		}
		
		return -1;
	}

	//For heuristic, checks if board is one away from winning for AI
	public boolean oneAway(String aiChar, String playerChar) {
		for (int i=0; i <3; i++) {
			//checks rows to see if one away from winning
			if (board[i*3].equals(aiChar) && board[1+i*3].equals(aiChar) && !board[2+i*3].equals(playerChar)) {
				return true;
			} else if (!board[i*3].equals(playerChar) && board[1+i*3].equals(aiChar) && board[2+i*3].equals(aiChar)) {
				return true;
			} else if (board[i*3].equals(aiChar) && board[2+i*3].equals(aiChar) && !board[1+i*3].equals(playerChar)) {
				return true;
			}
			
			//checks columns to see if one away from winning
			if (board[i].equals(aiChar) && board[i+3].equals(aiChar) && !board[i+6].equals(playerChar)) {
				return true;
			} else if (board[i+6].equals(aiChar) && board[i+3].equals(aiChar) && !board[i].equals(playerChar)) {
				return true;
			} else if (board[i].equals(aiChar) && board[i+6].equals(aiChar) && !board[i+3].equals(playerChar)) {
				return true;
			}
		}
		
		//checks diagonals to see if one away from winning
		if (board[4].equals(aiChar)) {
			if (board[0].equals(aiChar) && !board[8].equals(playerChar)) {
				return true;
			} else if (board[2].equals(aiChar) && !board[6].equals(playerChar)) {
				return true;
			} else if (board[6].equals(aiChar) && !board[2].equals(playerChar)) {
				return true;
			} else if (board[8].equals(aiChar) && !board[0].equals(playerChar)) {
				return true;
			}
		} else if (!board[4].equals(playerChar)) {
			if (board[0].equals(aiChar) && board[8].equals(aiChar)) {
				return true;
			} else if (board[2].equals(aiChar) && board[6].equals(aiChar)) {
				return true;
			}
		}
		
		return false;
	}
}

