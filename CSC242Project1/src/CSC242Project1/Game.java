package CSC242Project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {
	Board board;
	String playerChar = " ";
	String AIchar = " ";
	long timePerMove = (long) 0.0;
	int boardsAnalyzed = 0;
	
	boolean saveChar = false; //Change to 'true' and new games will begin instantly with the same character choice ('x' or 'o') instead of prompting again which one you want to be
	
	//Create board and begin game
	void newGame() throws IOException, InterruptedException {
		board = new Board();
		board.initBoard();
		
		/* hard code board different */
		/* ----- TEST CODE -----*/
		/*String[] newSpots = new String[9];
		for (int i=0; i < newSpots.length; i++) {
			newSpots[i] = String.valueOf(i+1);
		}
		newSpots[0] = "X";
		newSpots[1] = "O";
		newSpots[2] = "X";
		newSpots[7] = "O";
		board.testBoard(newSpots);*/
		/* ----- TEST CODE -----*/
		
		//Pick which piece you want to be in the game
		System.err.println("Enter 'X' or 'O' to be either respectively: ");
		while (playerChar != "X" && playerChar != "O") {
			//get input
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader bd = new BufferedReader(isr);
			String name = bd.readLine();
			
			if (name.equalsIgnoreCase("X")) {
				playerChar = "X";
			} else if (name.equalsIgnoreCase("O")){
				playerChar = "O";
			} else {
				System.err.println("Please enter either 'X' or 'O': ");
			}
		}
		//Specify which player goes first
		if (playerChar == "X") {
			AIchar = "O";
			System.err.println("As player 'X' your turn is first: ");
		} else {
			AIchar = "X";
			System.err.println("As player 'O' your turn is second: ");
		}
		
		board.printBoard();
		
		//Begin game
		playGame();
	}
	
	//Main function for game
	void playGame() throws IOException, InterruptedException {
		String winner = null;
		
		//Main loop for the game, until a winner is decided
		while (winner == null) {
			//Player goes first
			if (playerChar.equals("X")) {
				
				playerTurn();
				
				//check if won or tie
				if (board.checkVictory() != null) {
					winner = board.checkVictory();
					break;
				}
				if (board.checkTie() == true) {
					winner = "No one";
					break;
				}
				
				AIturn();
				
				//check if won or tie
				if (board.checkVictory() != null) {
					winner = board.checkVictory();
					break;
				}
				if (board.checkTie() == true) {
					winner = "No one";
					break;
				}
				
			//AI goes first
			} else if (playerChar.equals("O")) {
				
				AIturn();
				
				//check if won or tie
				if (board.checkVictory() != null) {
					winner = board.checkVictory();
					break;
				}
				if (board.checkTie() == true) {
					winner = "No one";
					break;
				}
				
				playerTurn();
				
				//check if won or tie
				if (board.checkVictory() != null) {
					winner = board.checkVictory();
					break;
				}
				if (board.checkTie() == true) {
					winner = "No one";
					break;
				}
				
			} else {
				System.err.println("Your character is neither 'X' nor 'O' please try again. ");
			}
		}
		board.printBoard();
		
		//Print victory/loss messages
		System.err.println("'" + winner + "' won the game! ");
		if (playerChar.equals(winner)) {
			System.err.println("Congrats on your victory! ");
		} else {
			System.err.println("Better luck next time! ");
		}
		
		if (saveChar == true) {
			newGame();
		}
		return;
	}
	
	//Players turn
	void playerTurn() throws IOException, InterruptedException {
		//Print the board at start of turn
		//board.printBoard();
		
		System.err.print("Player turn, enter the number of a spot that is not taken:\n");
		boolean userChoose = false;
		
		//Loop until user chooses a position
		while (!userChoose) {
			
			//Get input
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader bd = new BufferedReader(isr);
			String userInput = bd.readLine();
			
			//Check input and put in a spot
			if (userInput != "X" && userInput != "Y" && userInput != " ") {
				for (int i=0; i < board.board.length; i++) {
					if (userInput.equals(board.board[i])) {
						board.board[i] = playerChar;
						userChoose = true;
					}
				}
			}
			
			if (userChoose == false) {
				System.err.println("Please enter the 'number' of a position on the board that is empty... ");
			}
		}
		//Print the board
		board.printBoard();
		
	}
	
	//AI turn
	void AIturn() throws InterruptedException {
		timePerMove = System.currentTimeMillis();
		boardsAnalyzed = 0;
		//Print the board
		//board.printBoard();
				
		//Set board to AI's change (Minimax algorithm to determine)
		board.board = AIminiMaxBegin(board);
		System.err.println("AI took "+(System.currentTimeMillis() - timePerMove) + " Milliseconds to analyze "+boardsAnalyzed+" boards and completed it's turn.");
		
		//Print the board after AI turn
		board.printBoard();
		
	}
	
	//Begin the MinMax recursion here
	String[] AIminiMaxBegin(Board board) {
		int positionAIMovedIn = 0;
		
		//Create a copy of the board
		Board newBoard = new Board();
		newBoard.initBoard2(board);
		
		//Create another copy of the board to return
		Board returnBoard = new Board();
		
		//Save edited baord, to use alpha beta pruning with
		Board editedBoard = new Board();
		editedBoard.initBoard2(newBoard.editEmptySpot(0, AIchar));
		positionAIMovedIn = newBoard.getEditEmptySpot(0);
		
		//Set currentMax to MiniMax of first edited board (in first spot only)
		int currentMax = MiniMax(editedBoard, true, newBoard);
		boardsAnalyzed++;
		
		/* Alpha-beta pruning addition */
		newBoard.alpha = Math.max(editedBoard.beta, newBoard.alpha);
		/*Alpha-beta pruning addition above */
		
		/* TESTING CODE */
		//System.err.println(currentMax);
		//System.err.println("Alpha: "+ newBoard.alpha +" Beta: " +  newBoard.beta);
		
		//Set the initial returnBoard to edit first available space (in-case no other options are better)
		returnBoard.initBoard2(editedBoard);
		
		//Max, (AI TURN since first run of minimax)
		for (int i=0; i < newBoard.numberEmptySpaces()-1; i++) {
			/* TESTING CODE */
			//System.err.println("Currently Editing in Position: " + i);
			
			editedBoard.initBoard2(newBoard.editEmptySpot(i+1, AIchar));
			boardsAnalyzed++;
			
			//Inputs a AIchar in a spot and calls MiniMax on next board where it is now AIturn
			if (Math.max(currentMax, MiniMax(editedBoard, true, newBoard)) > currentMax) {
				positionAIMovedIn = newBoard.getEditEmptySpot(i+1);
				returnBoard.initBoard2(editedBoard);
				currentMax = Math.max(currentMax, MiniMax(editedBoard, true, newBoard));
				/* TESTING CODE */
				//System.err.println("Print just changed return board: " + currentMax);
				//returnBoard.printBoard();
			}
			
			/* Alpha-beta pruning addition */
			newBoard.alpha = Math.max(editedBoard.beta, newBoard.alpha);
			/*Alpha-beta pruning addition above */
			
			
			/* TESTING CODE */
			/*else {
				//System.err.println("Print just DID NOT change return board: " + Math.max(MiniMax(newBoard.editEmptySpot(i, AIchar), true), MiniMax(newBoard.editEmptySpot(i+1, AIchar), true)) + "is not > than " + currentMax);
				//returnBoard.printBoard();
			}*/
			//System.err.println("Alpha: "+ returnBoard.alpha +" Beta: " +  returnBoard.beta);
			//System.err.println("New BOARD: Alpha: "+ newBoard.alpha +" Beta: " +  newBoard.beta);

			
		}
		
		//TRY CATCH SLEEP 15 milliseconds so stderr and stdout don't mix
		try {
			Thread.sleep(15);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(positionAIMovedIn+1);
		//TRY CATCH SLEEP 15 milliseconds so stderr and stdout don't mix
		try {
			Thread.sleep(15);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return returnBoard.board;
	}
	
	int MiniMax(Board newBoard, boolean isPlayerTurn, Board previousBoard) {
		/* TESTING CODE */
		/*System.err.println("Current board: ");
		newBoard.printBoard();
		System.err.println("Previous board: ");
		previousBoard.printBoard();*/
		/*if ((newBoard.alpha > -10 || newBoard.beta < 10) && (newBoard.alpha != 1 || newBoard.beta != -1)) {
			System.err.println("Inside minimax: Alpha: "+ newBoard.alpha +" Beta: " +  newBoard.beta);
		}*/
		
		//Check if terminal state
		if (newBoard.checkVictory() != null) {
			
			/* Alpha-beta pruning return */
			if (isPlayerTurn == false) {
				newBoard.alpha = Math.max(newBoard.alpha, -10);
			} else {
				newBoard.beta = Math.min(newBoard.beta, 10);
			}
			/* Alpha-beta pruning addition above */
			
			if (newBoard.checkVictory().equals(AIchar)) {
				return 1;
			} else if (newBoard.checkVictory().equals(playerChar)) {
				return -1;
			}
		}
		
		//Check if tie (terminal state)
		if (newBoard.checkTie() == true) {
			
			/* Alpha-beta Pruning addition */
			//Player just went and tie
			if (isPlayerTurn == false) {
				newBoard.alpha = Math.max(newBoard.alpha, 0);
			} else {
				newBoard.beta = Math.min(newBoard.beta, 0);
			}
			/* Alpha-beta Pruning addition above */
			
			/* TESTING CODE */
			//System.err.println("No one won:");
			//newBoard.printBoard();
			
			return 0;
		}
		
		//Check if tie backup just in-case (terminal state)
		if (newBoard.numberEmptySpaces() == 0) {
			/* Alpha-beta Pruning addition */
			//Player just went and tie
			if (isPlayerTurn == false) {
				newBoard.alpha = Math.max(newBoard.alpha, 0);
			} else {
				newBoard.beta = Math.min(newBoard.beta, 0);
			}
			/* Alpha-beta Pruning addition above */
			return 0;
		}
		int testCurrentVal = 0;
		int currentVal = -50; //init lower at first
		testCurrentVal = currentVal;
		if (isPlayerTurn == false) {
			//max, AI TURN
			Board editedBoard = new Board();
			editedBoard.initBoard2(newBoard.editEmptySpot(0, AIchar));
			
			testCurrentVal = currentVal;
			currentVal = MiniMax(editedBoard, true, newBoard);
			if (testCurrentVal != currentVal) {
				newBoard.alpha = currentVal;
			}
			boardsAnalyzed++;
			
			/* Alpha-beta pruning addition */
			//newBoard.alpha = Math.max(editedBoard.beta, newBoard.alpha);
			if (newBoard.alpha > previousBoard.beta) {
				return currentVal;
			}
			/*Alpha-beta pruning addition above */
			
			//If more then one empty spot, test each and get the greater value
			for (int i=0; i < newBoard.numberEmptySpaces()-1; i++) {
				boardsAnalyzed++;
				editedBoard.initBoard2(newBoard.editEmptySpot(i+1, AIchar));
				//Inputs a AIchar in a spot and calls MiniMax on next board where it is now AIturn
				testCurrentVal = currentVal;
				currentVal = Math.max(currentVal, MiniMax(editedBoard, true, newBoard));
				if (testCurrentVal != currentVal) {
					newBoard.alpha = currentVal;
				}
				
				/* Alpha-beta pruning addition */
				if (newBoard.alpha > previousBoard.beta) {
					return currentVal;
				}
				/*Alpha-beta pruning addition above */
			}
			
			return currentVal;
			
		} else {
			//Initialize higher, so it will go lower guarnteed
			currentVal = 10;
			
			//If only one empty spot, insert into that position
			Board editedBoard = new Board();
			editedBoard.initBoard2(newBoard.editEmptySpot(0, playerChar));
			
			testCurrentVal = currentVal;
			currentVal = MiniMax(editedBoard, false, newBoard);
			if (testCurrentVal != currentVal) {
				newBoard.beta = currentVal;
			}
			boardsAnalyzed++;
			
			/* Alpha-beta pruning addition */
			//newBoard.beta = Math.min(newBoard.beta, editedBoard.alpha);
			if (newBoard.beta < previousBoard.alpha) {
				return currentVal;
			}
			/*Alpha-beta pruning addition above */
				
			//min, PLAYER TURN
			for (int i=0; i < newBoard.numberEmptySpaces()-1; i++) {
				boardsAnalyzed++;
				editedBoard.initBoard2(newBoard.editEmptySpot(i+1, playerChar));
				//Inputs a playerCharacter in a spot and calls MiniMax on next board where it is now AIturn
				testCurrentVal = currentVal;
				currentVal =  Math.min(currentVal, MiniMax(editedBoard, false, newBoard));
				if (testCurrentVal != currentVal) {
					newBoard.beta = currentVal;
				}
				/* Alpha-beta pruning addition */
				//newBoard.beta = Math.min(newBoard.beta, editedBoard.alpha);
				if (newBoard.beta < previousBoard.alpha) {
					return currentVal;
				}
				/*Alpha-beta pruning addition above */
			}
			return currentVal;
		}
	}
}
