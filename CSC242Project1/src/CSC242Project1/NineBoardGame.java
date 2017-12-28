package CSC242Project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NineBoardGame {
	NineBoard nBoard;
	int currentBoard = -1; //Board that can currently be used.
	String playerChar = " ";
	String AIchar = " ";
	long timePerMove = (long) 0.0;
	int boardsAnalyzed = 0;
	int boardChangedByAI = 0;
	int depthIncrease = 0;
	int turnsTaken = 0;
	boolean alphaBeta = true; //to turn on and off easier (no longer necessary but keeping anyways)
	
	boolean saveChar = false; //Change to 'true' and new games will begin instantly with the same character choice ('x' or 'o') instead of prompting again which one you want to be
	
	public void newGame() throws IOException, InterruptedException {
		//Init Variables (necessary in-case 'saveChar' is set to true)
		nBoard = new NineBoard();
		nBoard.initBoard();
		currentBoard = -1;
		turnsTaken = 0;
		depthIncrease = 0;
		
		//Choose character
		System.err.println("Enter 'X' or 'O' to be either respectively:");
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
				System.err.println("Please enter either 'X' or 'O':");
			}
		}
		
		//Print blank board once to begin
		nBoard.printBoard();
				
				
				
		//Specify which player goes first
		if (playerChar == "X") {
			AIchar = "O";
			System.err.println("As player 'X' your turn is first:");
		} else {
			AIchar = "X";
			System.err.println("As player 'O' your turn is second:");
		}
				
		//Begin game
		playGame();
	}
	
	public void playGame() throws IOException, InterruptedException {
		String winner = null;
		
		//Main loop for the game, until a winner is decided
		while (winner == null) {
			//every 10 turns increase depth 1
			turnsTaken = turnsTaken+2;
			if (turnsTaken%10 == 0 && turnsTaken > 0) {
				depthIncrease++;
			}
			//Player goes first
			if (playerChar.equals("X")) {
				
				playerTurn();
				
				//check if won or tie
				if (nBoard.checkVictory() != null) {
					winner = nBoard.checkVictory();
					break;
				}
				if (nBoard.checkAllTie() == true) {
					winner = "No one";
					break;
				}
				
				AIturn();
				
				//check if won or tie
				if (nBoard.checkVictory() != null) {
					winner = nBoard.checkVictory();
					break;
				}
				if (nBoard.checkAllTie() == true) {
					winner = "No one";
					break;
				}
				
			//AI goes first
			} else if (playerChar.equals("O")) {
				
				AIturn();
				
				//check if won or tie
				if (nBoard.checkVictory() != null) {
					winner = nBoard.checkVictory();
					break;
				}
				if (nBoard.checkAllTie() == true) {
					winner = "No one";
					break;
				}
				
				playerTurn();
				
				//check if won or tie
				if (nBoard.checkVictory() != null) {
					winner = nBoard.checkVictory();
					break;
				}
				if (nBoard.checkAllTie() == true) {
					winner = "No one";
					break;
				}
				
			} else {
				System.err.println("Your character is neither 'X' nor 'O' please try again.");
			}
		}
		
		nBoard.printBoard();
		
		//Print victory/loss messages
		System.err.println("'" + winner + "' won the game!");
		if (playerChar.equals(winner)) {
			System.err.println("Congrats on your victory!");
		} else {
			System.err.println("Better luck next time!");
		}
		
		if (saveChar) {
			newGame();
		}
		return;
	}
	
	public void playerTurn() throws IOException, InterruptedException {
		//If playerTurn is first and no board is chosen
		if (currentBoard == -1) {
			System.err.println("Please enter the number of which board you would like to play on first:");
			boolean userChoose = false;
			
			
			//Loop until user chooses a position
			while (!userChoose) {
				
				//Get input
				InputStreamReader isr = new InputStreamReader(System.in);
				BufferedReader bd = new BufferedReader(isr);
				String userInput = bd.readLine();
				
				//Check input and put in a spot
				if (userInput != "X" && userInput != "Y" && userInput != " ") {
					for (int i=0; i < nBoard.boards.length; i++) {
						if (userInput.equals(nBoard.boards[0].board[i])) {
							currentBoard = Integer.parseInt(nBoard.boards[0].board[i])-1;
							userChoose = true;
						}
					}
				}
			}
		} else if (nBoard.checkAllTie() == false && nBoard.boards[currentBoard].checkTie() == true){
			System.err.println("Any board can be played on since the board chosen is a tie, please enter the Number of the board you want to play on '[1-9]': ");
			boolean userChoose = false;
			
			//Loop until user chooses a position
			while (!userChoose) {
				
				//Get input
				InputStreamReader isr = new InputStreamReader(System.in);
				BufferedReader bd = new BufferedReader(isr);
				String userInput = bd.readLine();
				
				//Check input and put in a spot
				if (userInput != "X" && userInput != "Y" && userInput != " ") {
					for (int i=0; i < nBoard.boards.length; i++) {
						if (userInput.equals(nBoard.boards[0].board[i])) {
							if (nBoard.boards[i].checkTie() == false) {
								currentBoard = Integer.parseInt(nBoard.boards[0].board[i])-1;
								userChoose = true;
							}
						}
					}
				}
				
				if (userChoose == false) {
					System.err.println("Please enter the Number '[1-9]' of a board that is not in a tie to play on: ");
				}
			}
		}
		
		nBoard.printBoard();
		System.err.println("The current board is " + (currentBoard+1) + ", please enter the Number '[1-9]' of a position that is not taken:");
		
		boolean userChoosePosition = false;
		//Loop until user chooses a position
		while (!userChoosePosition) {
			
			//Get input
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader bd = new BufferedReader(isr);
			String userInput = bd.readLine();
			
			//Check input and put in a spot
			if (userInput != "X" && userInput != "Y" && userInput != " ") {
				for (int i=0; i < nBoard.boards[currentBoard].board.length; i++) {
					if (userInput.equals(nBoard.boards[currentBoard].board[i])) {
						nBoard.boards[currentBoard].board[i] = playerChar;
						userChoosePosition = true;
						currentBoard = Integer.parseInt(userInput)-1;
					}
				}
			}
			
			if (userChoosePosition == false) {
				System.err.println("Please enter the 'number' of a position on the board that is empty...");
			}
		}
		
	}
	
	/* ------------------------
	 * 
	 *    AI Code Starts Here
	 * 
	   ------------------------*/
	public void AIturn() {
		timePerMove = System.currentTimeMillis();
		boardsAnalyzed = 0;
		
		if (currentBoard == -1) {
			//AI plays first board if it goes first
			currentBoard = 1;
		} else if (nBoard.checkAllTie() == false && nBoard.boards[currentBoard].checkTie() == true){
			for (int i=0; i < nBoard.boards.length; i++) {
				if (nBoard.boards[i].checkTie() == false) {
					currentBoard = i;
					break;
				}
			}
		}
		
		//Set board to AI's change (Minimax algorithm to determine)
		nBoard.boards[currentBoard] = AIminiMaxBegin(nBoard);
		//board.board = AIminiMaxBegin(board);
		System.err.println("AI took "+(System.currentTimeMillis() - timePerMove) + " Milliseconds to analyze "+boardsAnalyzed+" boards.");
		currentBoard = boardChangedByAI;
		//TRY CATCH SLEEP 15 milliseconds so stderr and stdout don't mix
		try {
			Thread.sleep(15);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(currentBoard+1);
		//TRY CATCH SLEEP 15 milliseconds so stderr and stdout don't mix
		try {
			Thread.sleep(15);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Print the board
		//board.printBoard();
	}
	
	//Begin the MinMax recursion here
	Board AIminiMaxBegin(NineBoard nBoard2) {
		
		int depth = 0;
		int playerWinsNext = 0;
		
		//If the currentBoard is a tie and the entire baord is not a tie, AI can choose any board to play on.
		if (nBoard2.boards[currentBoard].checkTie() && nBoard2.checkAllTie() == false) {
			for (int i=0; i < nBoard2.boards.length; i++) {
				if (!nBoard2.boards[i].checkTie()) {
					currentBoard = i;
				}
			}
		}
		
		//Create a copy of the board
		NineBoard newNBoard = new NineBoard();
		newNBoard.initBoard2(nBoard2);
		
		//Create another copy of the board to return
		Board returnBoard = new Board();
		
		//Save edited baord, to use alpha beta pruning with
		NineBoard editedBoard = new NineBoard();
		int nextBoard = newNBoard.getEditEmptySpot(0, currentBoard);
		editedBoard.initBoard2(newNBoard.editEmptySpot(0, AIchar, currentBoard));
		boardsAnalyzed++;
		
		//If the player is one away from winning on the next board:
		if (editedBoard.boards[nextBoard].oneAway(playerChar, AIchar)) {
			//Subtract 1 more from value if player wins on exact next move
			playerWinsNext = -1;
		} else {
			playerWinsNext = 0;
		}
		
		//If there is a victory board possible this turn, do it right away
		if (editedBoard.checkVictory() != null) {
			if (editedBoard.checkVictory().equals(AIchar)) {
				returnBoard.initBoard2(editedBoard.boards[currentBoard]);
				return returnBoard;
			}
		}
		
		//Set currentMax to MiniMax of first edited board (in first spot only)
		boardsAnalyzed++;
		int currentMax = nMiniMax(editedBoard, true, newNBoard, nextBoard, (depth+1)) + playerWinsNext;
		
		/* Alpha-beta pruning addition */
		if (alphaBeta) {
			//System.err.println("Beta: " + editedBoard.beta);

			newNBoard.alpha = Math.max(editedBoard.beta, newNBoard.alpha);
		}
		/*Alpha-beta pruning addition above */
		
		/* TESTING CODE */
		//System.err.println(currentMax);
		//System.err.println("Alpha: "+ editedBoard.alpha +" Beta: " +  editedBoard.beta);
		
		//Set the initial returnBoard to edit first available space (in-case no other options are better)
		boardChangedByAI = nextBoard;
		returnBoard.initBoard2(editedBoard.boards[currentBoard]);
		
		
		//System.err.println((nextBoard+1) + ": mini: " + currentMax + " current: " + currentMax + " a: " + editedBoard.alpha + " b: " + editedBoard.beta);
		
		
		//Max, (AI TURN since first run of minimax)
		//First run through, only edits the current board it is allowed to
		for (int i=0; i < newNBoard.boards[currentBoard].numberEmptySpaces()-1; i++) {
			/* TESTING CODE */
			//System.err.println("Currently Editing in Position: " + i);
			
			
			nextBoard = newNBoard.getEditEmptySpot(i+1, currentBoard);
			editedBoard.initBoard2(newNBoard.editEmptySpot(i+1, AIchar, currentBoard));
			boardsAnalyzed++;
			
			//If the player is one away from winning on the next board:
			if (editedBoard.boards[nextBoard].oneAway(playerChar, AIchar)) {
				playerWinsNext = -1;
			} else {
				playerWinsNext = 0;
			}
			
			//If there is a victory board possible this turn, do it right away
			if (editedBoard.checkVictory() != null) {
				if (editedBoard.checkVictory().equals(AIchar)) {
					returnBoard.initBoard2(editedBoard.boards[currentBoard]);
					break;
				}
			}
			
			//Incase it is a new best move, save the move so don't have to re-do it (in miniMaxVal)
			//Inputs a AIchar in a spot and calls MiniMax on next board where it is now AIturn
			int miniMaxVal = nMiniMax(editedBoard, true, newNBoard, nextBoard, (depth+1)) + playerWinsNext;
			
			/* Alpha-beta pruning addition */
			if (alphaBeta) {
				//System.err.println("Beta: " + editedBoard.beta);
				newNBoard.alpha = Math.max(editedBoard.beta, newNBoard.alpha);
			}
			/*Alpha-beta pruning addition above */
			
			if (Math.max(currentMax, (miniMaxVal)) > currentMax) {
				returnBoard.initBoard2(editedBoard.boards[currentBoard]); //Set return board to be that board
				boardChangedByAI = nextBoard; //Set boardChangeByAI to nextBoard (so player will play on this board if none better)
				currentMax = miniMaxVal; //Set currentMax to miniMaxVal
			}
			
		}
		return returnBoard;
	}
	
	int nMiniMax(NineBoard newBoard, boolean isPlayerTurn, NineBoard previousBoard, int boardToPlaceOn, int depth) {
		/* TESTING CODE */
		/*System.err.println("Current board: ");
		try {
			newBoard.printBoard();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.err.println("Previous board: ");
		try {
			previousBoard.printBoard();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		//System.err.println("Current boards analyzed: " + boardsAnalyzed + " boardToPlaceOn: " + boardToPlaceOn);
		/* END TESTING CODE */
		
		//---------------------
		//   TERMINAL TEST
		//---------------------
		if (newBoard.checkVictory() != null) {
			
			/* Alpha-beta pruning below */
			if (alphaBeta) {
				if (isPlayerTurn == false) {
					newBoard.alpha = Math.max(newBoard.alpha, -10);
				} else {
					newBoard.beta = Math.min(newBoard.beta, 10);
				}
			}
			/* Alpha-beta pruning addition above */
			
			if (newBoard.checkVictory().equals(AIchar)) {
				return 10;
			} else if (newBoard.checkVictory().equals(playerChar)) {
				return -10;
			}
		}
		
		//Check if tie (terminal state)
		if (newBoard.checkTie() == true) {
			
			/* Alpha-beta Pruning addition */
			if (isPlayerTurn == false) {
				newBoard.alpha = Math.max(newBoard.alpha, 0);
			} else {
				newBoard.beta = Math.min(newBoard.beta, 0);
			}
			/* Alpha-beta Pruning addition above */

			return 0;
		}
		
		//Check if tie backup just in-case (terminal state)
		if (newBoard.numberEmptySpaces(boardToPlaceOn) == 0) {
			/* Alpha-beta Pruning addition */
			if (isPlayerTurn == false) {
				newBoard.alpha = Math.max(newBoard.alpha, 0);
			} else {
				newBoard.beta = Math.min(newBoard.beta, 0);
			}
			/* Alpha-beta Pruning addition above */
			return 0;
		}
		
		
		if (depth > (5 + depthIncrease)) {
			//decide if board looks good or bad and assign it a value (valueOfMove)
			int valueOfMove = 0;
			
			//If AI one move away from winning:
			if (newBoard.oneAwayFromVictory(AIchar, playerChar)) {
				
				valueOfMove += newBoard.countOneAway(AIchar, playerChar);
			}
			//If Player one away from winning
			if (newBoard.oneAwayFromLosing(AIchar, playerChar)) {

				valueOfMove -= newBoard.countOneAwayLosing(AIchar, playerChar);
			}
			
			/* Alpha-beta pruning below */
			if (isPlayerTurn == false) {
				newBoard.alpha = Math.max(newBoard.alpha, valueOfMove);
			} else {
				newBoard.beta = Math.min(newBoard.beta, valueOfMove);
			}	
			/* Alpha-beta Pruning addition above */
			
			return valueOfMove;
		}
		
		//---------------------
		//  END TERMINAL TEST
		//---------------------
		int testCurrentValChange = 0;
		int currentVal = -50; //init lower at first
		testCurrentValChange = currentVal;
		int nextBoard = -1; //init for use later in if/else if
		if (isPlayerTurn == false) {
			//max, AI TURN
			NineBoard editedBoard = new NineBoard();
			nextBoard = newBoard.getEditEmptySpot(0, boardToPlaceOn); //Int of board edited
			editedBoard.initBoard2(newBoard.editEmptySpot(0, AIchar, boardToPlaceOn)); //Board edited
			
			boardsAnalyzed++;
			testCurrentValChange = currentVal;
			currentVal = nMiniMax(editedBoard, true, newBoard, nextBoard, (depth+1));
			
			//If highest value seen changes, change alpha value
			if (testCurrentValChange != currentVal) {
				newBoard.alpha = currentVal;
			}
			
			/* Alpha-beta pruning addition */
			if (alphaBeta) {
				if (newBoard.alpha > previousBoard.beta) {
					return currentVal;
				}
			}
			/*Alpha-beta pruning addition above */
			
			//If more then one empty spot, test each and get the greater value
			for (int i=0; i < newBoard.numberEmptySpaces(boardToPlaceOn)-1; i++) {
				nextBoard = newBoard.getEditEmptySpot(i+1, boardToPlaceOn);
				editedBoard.initBoard2(newBoard.editEmptySpot(i+1, AIchar, boardToPlaceOn));
				//Inputs a AIchar in a spot and calls MiniMax on next board where it is now AIturn
				boardsAnalyzed++;
				testCurrentValChange = currentVal;
				currentVal = Math.max(currentVal, nMiniMax(editedBoard, true, newBoard, nextBoard, (depth+1)));
				//If highest value seen changes, change alpha value (below)
				if (currentVal != testCurrentValChange) {
					newBoard.alpha = currentVal;
				}
				
				/* Alpha-beta pruning addition */
				if (alphaBeta) {
					if (newBoard.alpha > previousBoard.beta) {
						return currentVal;
					}
					
				}
				/*Alpha-beta pruning addition above */
			}
			
			return currentVal;
			
		} else {
			//PLAYER TURN = TRUE
			//Initialize higher, so it will go lower guarnteed
			currentVal = 50;
			
			//If only one empty spot, insert into that position
			NineBoard editedBoard = new NineBoard();
			nextBoard = newBoard.getEditEmptySpot(0, boardToPlaceOn);
			editedBoard.initBoard2(newBoard.editEmptySpot(0, playerChar, boardToPlaceOn));
			
			boardsAnalyzed++;
			testCurrentValChange = currentVal;
			currentVal = nMiniMax(editedBoard, false, newBoard, nextBoard, (depth+1));
			//If lowest value seen changes, change beta
			if (testCurrentValChange != currentVal) {
				newBoard.beta = currentVal;
			}
			
			/* Alpha-beta pruning addition */
			if (alphaBeta) {
				//newBoard.beta = Math.min(newBoard.beta, editedBoard.alpha);
				if (newBoard.beta < previousBoard.alpha) {
					return currentVal;
				}
			}
			/*Alpha-beta pruning addition above */
			
			//min, PLAYER TURN
			for (int i=0; i < newBoard.numberEmptySpaces(boardToPlaceOn)-1; i++) {
				nextBoard = newBoard.getEditEmptySpot(i+1, boardToPlaceOn);
				editedBoard.initBoard2(newBoard.editEmptySpot(i+1, playerChar, boardToPlaceOn));
				//Inputs a playerCharacter in a spot and calls MiniMax on next board where it is now AIturn
				
				boardsAnalyzed++;
				testCurrentValChange = currentVal;
				currentVal =  Math.min(currentVal, nMiniMax(editedBoard, false, newBoard, nextBoard, (depth+1)));
				if (testCurrentValChange != currentVal) {
					newBoard.beta = currentVal;
				}
				/* Alpha-beta pruning addition */
				if (alphaBeta) {
					//newBoard.beta = Math.min(newBoard.beta, editedBoard.alpha);
					if (newBoard.beta < previousBoard.alpha) {
						return currentVal;
					}
				}
				/*Alpha-beta pruning addition above */
			}
			
			return currentVal;
		}
	}
}
