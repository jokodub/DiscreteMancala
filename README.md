# Mancala Simulator, John Vezzola's Final Project
For Professor Guha, COT3100H. Analyzing the Game Theory of Mancala.
This code is used to simulate thousands of possible games against each other.
## Terminology
Throughout this document and all files, I use this terminology:
- A Mancala board has two **lanes**, one on each side of the board.
- Each lane is made of (by default 6) **pits**, capped off with a large **pot** that is used as the player's score.
- Each pit is filled with **pieces** that can be moved around the board.
- The pits are numbered with their **position**, or distance from their pot.
- Players can have a specific **Strategy** they like to play, for example taking from the pit with the most pieces.
## Rules of Mancala
On a player's turn, they choose from one of their pits that contains at least one piece. They take all of the pieces
out of that pit and begin to "sow" them across the board, placing one piece in each pit as they move along counterclockwise (skipping over opponent's pot). 
You cannot take pieces out of your pot. 

If your final piece lands perfectly in your pot, you earn a bonus turn.

If your final piece lands in an empty pit in your lane AND the adjacent opponent's pit has pieces, you "capture" both pits and place them into your pot. 
This does not grant you a bonus turn. 

If a player has no moves available, the opponent captures all pieces remaining in their lane, and the game is over. 

The game is over when there are no more pieces in play. The winner of a game is whoever has the majority of the pieces, or a tie if even.

## How to use Manager
The Manager main class handles the overhead of playing a game. Uncomment which mode you want.

Referee.play() will allow you to simulate a single game as you want.
1. Boardstate to begin play at (*ex: new Board()*)
2. Strategy for P1 (*ex: new Human()*)
3. Strategy for P2
4. Verbose printing (*required for Human play, default: true*)
5. Fast: End the game early, don't play until every piece is gone. (*default: false*)

simulate() will simulate thousands of games and summarize the results.
1. Number of games to run per matchup
2. Pieces each pit begins with
3. Pits per lane
## /src/strategies
Contains the code of each Strategy available for testing. Each Strategy implements the Strategy class, and can call any of Referee or Board's methods. 
Also contains a Human strategy to play against a Strategy yourself. 
## /src/structure
Contains the code of the gameplay and pieces of the board.
- **Pit:** A single pit/pot of a lane.
- **Board:** Represents an entire board with methods for moving and examining the pits.
- **Referee:** Simulates a game, handling errors and declaring the game won.
- **Manager:** Handles overhead of simulating many games.
## /out
Any output files are written here
