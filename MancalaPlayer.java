/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * MancalaPlayer class
 * This class creates two players and referees
 * the game between them.
 */

package DiscreteMancala;

public class MancalaPlayer {
    
    public static void main(String[] args){

        //add: arg for printing text and not
        boolean verbose = false;
        if(args.length != 0 && args[0].equals("v")) { verbose = true; }

        Board b = new Board();

        /* All strategies for players
         * Human = Prompts stdin
         * 
         * ---Greedy---
         * Looks no further than the current state of the board
         * 
         * Randomized = chooses a random pit 
         * FarClose = chooses the farthest pit from pot it can
         * CloseFar = chooses the closest pit from pot it can
         * Biggest = chooses the largest pit, randomly if equal
         * Smallest = chooses the smallest pit, randomly if equal
         * 
         * ---Minimax Weights---
         * Different strategies that minimax will prioritize 
         * 
         * Most empty
         * Captures
         * Most spread out (all 1s vs one 6)
         * 
         */

        //Create your two players here.
        Strategy p1 = new Human(); //Player 1
        Strategy p2 = new Human(); //Player 2

        //Game loop
        while(true){
            //Perform player 1's move
            int move1 = p1.chooseMove(b);
            if(verbose) System.out.println("Player 1 chooses position " + move1);
            b.move(true, move1); 
            if(verbose) b.printBoard();
            if(b.gameOver()) break;

            //Perform player 2's move
            int move2 = p2.chooseMove(b);
            if(verbose) System.out.println("Player 2 chooses position " + move2);
            b.move(false, move2);
            if(verbose) b.printBoard();
            if(b.gameOver()) break;
        }

        displayWinner(b);
        
    }

    public static void displayWinner(Board b){
        if(b.whoWinning())
            System.out.println("Player 1 wins!");
        else
            System.out.println("Player 2 wins!");
    }
}
