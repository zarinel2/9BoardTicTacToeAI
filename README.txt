(a) Contributors:

Name: William Tyler Wilson
Contact: wwils11@u.rochester.edu

(b) How to Build the Project:

Included is the zip file with CSC242Project1 directory. Inside terminal, cd inside src and CSC242Project1:
(assuming current working directory is where the ‘CSC242Project1’ un-zipped folder is)

Commands to run (after unzipping and changing directory to inside zip folder where READEME.txt is and the WriteUp.pdf): 

1.	cd CSC242Project1
2.	cd src
3.	cd CSC242Project1                           (this is inside the package now)
4.	javac Board.java NineBoard.java Game.java NineBoardGame.java testProgram.java
5.	cd ..                                                   (should be in working directory ‘src’ now)
6.	java CSC242Project1.testProgram

(c) How to run your project’s program(s) to demonstrate that it meets the requirements:

Once inside the program, it will prompt you with what commands to enter:
1. Enter ‘9’ or ‘1’ first to play either the 9 board or 1 board tic-tac-toe.
2. Regardless of which you choose, enter either ‘x’ or ‘o’ afterwards to pick your character (X goes first and O goes second)

If playing on the 9 board, then enter the number of the board you would like to play on ‘[1-9] where 1 is the top left and 9 is the bottom right. (3 is then the rightmost board in the top row etc.)
From this point onwards, only positions are necessary to enter where you want to place your ‘X’ or ‘O’ (positions being '[1-9]' that are not taken)

Once the program is running, it prints to stderr how long it takes to calculate each move and how many boards it analyses. To play against it, simply enter the number of which spot you want to take 
(shown on the board) ‘[1-9]’ and it will move as well, quickly following. If you re-run the program (‘java CSC242Project1.testProgram’) you can switch between 9 Board and 1 Board easily in the prompt given to
check the functionality of both independently.

Important: Since I didn't know if after each game it should prompt you for 'x' or 'o' again when automatically starting another game, I added a boolean 'saveChar' which is defaulted to false.
 If turned to 'true' in the code on Game.java and NineBoardGame.java, then it will not prompt for whether you are 'x' or 'o' after each game when automatically starting another game.

