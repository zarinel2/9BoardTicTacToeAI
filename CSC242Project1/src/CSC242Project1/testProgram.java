package CSC242Project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class testProgram {

	public static void main(String[] args) throws IOException, InterruptedException {
		//Pick which type of Game to Play:
		String gameType = "";
		boolean playAgain = true;
		Board board = new Board();
		board.initBoard();
		System.err.println("Enter '9' or '1' to play either Nine-Board Tic-Tac-Toe (9) or Normal Tic Tac Toe (1): ");
		while (gameType != "9" && gameType != "1") {
			//get input
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader bd = new BufferedReader(isr);
			String input = bd.readLine();
			
			if (input.equals("9")) {
				gameType = "9";
			} else if (input.equals("1")){
				gameType = "1";
			} else {
				System.err.println("Please enter either '9' or '1' to choose your Game Type: ");
			}
		}
		
		if (gameType.equals("9")) {
			//Begin Nine-Board Tic-Tac-Toe
			while (playAgain) {
				NineBoardGame game = new NineBoardGame();
				game.newGame();
				
				//--
				//In project assignment it says to instantly start another game so I removed this portion of the code (below) so it would continually start new games.
				//--
				
				/*System.err.println("Play again? (Enter 'Y' for Yes and Anything Else for No) \n");
				InputStreamReader isr = new InputStreamReader(System.in);
				BufferedReader bd = new BufferedReader(isr);
				String input = bd.readLine();
				
				if (input.equalsIgnoreCase("Y")) {
					System.err.println("A New Game of Nine-Board tic-tac-toe will begin! \n");
				} else {
					playAgain = false;
				}*/
			}
			
		} else {
			//Begin Normal Tic-Tac-Toe
			while (playAgain) {
				Game game = new Game();
				game.newGame();
				
				//--
				//In project assignment it says to instantly start another game so I removed this portion of the code (below) so it would continually start new games.
				//--
				
				/*System.err.println("Play again? (Enter 'Y' for Yes and Anything Else for No) ");
				InputStreamReader isr = new InputStreamReader(System.in);
				BufferedReader bd = new BufferedReader(isr);
				String input = bd.readLine();
				
				if (input.equalsIgnoreCase("Y")) {
					System.err.println("A New Game of One-Board (Normal) tic-tac-toe will begin! ");
				} else {
					playAgain = false;
				}*/
			}
		}
	}
}
