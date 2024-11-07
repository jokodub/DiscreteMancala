/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * MancalaPlayer class
 * This class creates two players and referees
 * the game between them.
 */

package DiscreteMancala;

public class Referee {
    
    public static void play(Strategy p1, Strategy p2, boolean verbose) {

        Board b = new Board();

        //Game loop
        while(true){
            //Perform player 1's move
            int move1 = p1.chooseMove(b);
            if(verbose) System.out.println("Player 1 chooses position " + move1);
            b.move(true, move1); 
            if(verbose) b.printBoard();
            if(b.gameOver()) 
                break;

            //Perform player 2's move
            int move2 = p2.chooseMove(b);
            if(verbose) System.out.println("Player 2 chooses position " + move2);
            b.move(false, move2);
            if(verbose) b.printBoard();
            if(b.gameOver()) 
                break;
        }

        displayWinner(b);
        
    }

    public static void displayWinner(Board b){
        if(b.whoWinning())
            System.out.println("Player 1 wins!");
        else
            System.out.println("Player 2 wins!");
    }

    /* Returns if the game is over, all pieces have been collected.
     * @return true if game over, false if pieces remaining
     */
    public static boolean gameOver(Board b){

    }

    /* Returns if the game has already been won, if a player has over
     * half of the pieces. Determines the winner earlier than a full game.
     * @return true if a player has half the pieces
     */
    public static boolean gameOverHalf(Board b){

    }

    
    public boolean gameOver(){
        int winningPieces = (initialPieces * pits) / 2; //Minimum pieces to have won
        
        //Game can be called over early if a player has enough pieces
        if(boardarr[getPotIndex(true)].getPieces() > winningPieces || 
        boardarr[getPotIndex(false)].getPieces() > winningPieces)
            return true;
        else
            return false;
    }

    /* Returns the user that is currently winning
     * Tie is returned as opponent winning
     * @return true for p1 winning, false for p2
     */
    public static int whoWinning(Board b){
        
        return b.getPieces(b.getPotIndex(true)) > b.getPieces(b.getPotIndex(false));
    }
}
