/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * 
 * Referee
 * Simulates one game between two different Strategies.
 */

package structure;
import strategies.*;

public class Referee {
    
    /* Play an entire game of mancala.
     * @param b as the Board to begin play on
     * @param p1,p2 as the Strategies for Player 1 and 2.
     * @param verbose to print the game out at each step for observing
     * @param fast as whether to call games early or play out in full
     * @return Player 1's final score, or -1 if an illegal move occurred.
     */
    public static int play(Board b, Strategy p1, Strategy p2, boolean verbose, boolean fast) {

        //Game loop
        int move1, move2;
        int status = 0; //Stores return of move(), -1 for illegal, 0 for normal, 1 for bonus
        int cheatFlag = 0; //Flag for illegal moves
        boolean finished = false; //Flag for game ending

        while(!finished)
        {
            //Perform player 1's move
            //Continue moving if extra moves are earned
            do{
                if(verbose){
                    System.out.println(b.toString());
                    printPos(b, true);
                }
                
                //Choose move
                move1 = p1.chooseMove(b.copy(), true);
                if(verbose) System.out.println("Player 1 chooses position " + move1);

                //Play the move
                status = b.move(true, move1); 
                if(status == 0)
                {
                    System.out.println("Player 1 has made an illegal move!");
                    cheatFlag = 1;
                    finished = true;
                    break; //Break out of player's loop
                }

                //Print extra text 
                if(verbose && (status & 2) == 2) System.out.println("Move again Player 1!");
                if(verbose && (status & 4) == 4) System.out.println("Capture!");
                if(verbose && (status & 8) == 8) System.out.println("No more available moves!");

                //Determine if game has ended, ignore any bonus moves
                if(fast)
                    if(gameOverHalf(b))
                    {
                        finished = true;
                        break;
                    }
                else
                    if(gameOver(b))
                    {
                        finished = true;
                        break;   
                    }          

            }while((status & 2) == 2 && (status & 8) != 8);

            //Break out of while if game forfeited/ended after Player 1
            if(finished || (status & 8) == 8)
                break; 

            //Perform player 2's move
            //Continue moving if extra moves are earned
            do{
                if(verbose){
                    printPos(b, false);
                    System.out.println(b.toString());
                }
                
                //Choose move
                move2 = p2.chooseMove(b.copy(), false);
                if(verbose) System.out.println("Player 2 chooses position " + move2);

                //Play the move
                status = b.move(false, move2); 
                if(status == 0)
                {
                    System.out.println("Player 2 has made an illegal move!");
                    cheatFlag = 2;
                    finished = true;
                    break; //Break out of player's loop
                }

                //Print extra text 
                if(verbose && (status & 2) == 2) System.out.println("Move again Player 2!");
                if(verbose && (status & 4) == 4) System.out.println("Capture!");
                if(verbose && (status & 8) == 8) System.out.println("No more available moves!");

                //Determine if game has ended, ignore any bonus moves
                if(fast)
                    if(gameOverHalf(b))
                    {
                        finished = true;
                        break;
                    }
                else
                    if(gameOver(b))
                    {
                        finished = true;
                        break;    
                    }         

            }while((status & 2) == 2 && (status & 8) != 8);

            //Break out of while if game forfeited after Player 2
            if(finished || (status & 8) == 8)
                break; 
            
        } //End game while loop

        if(cheatFlag != 0)
        {
            return cheatFlag == 1 ? 0 : b.getTotalPieces(); //Return 0 for P1 forfeit, or everything for P2 forfeit 
        }
        else
        {
            if(verbose) System.out.println(b.toString()); //Print final board
            displayWinner(b, p1, p2);
            return b.getPot(true); //Return Player 1's final score
        }
            
    }

    //Display the winner after the game has ended
    public static void displayWinner(Board b, Strategy p1, Strategy p2){

        int winner = whoWinning(b);

        if(winner == 1)
            System.out.println("Player 1 ("+p1.toString()+") wins! " + b.getPot(true) + "-" + b.getPot(false));
        else if(winner == 2)
            System.out.println("Player 2 ("+p2.toString()+") wins! " + b.getPot(false) + "-" + b.getPot(true));
        else
            System.out.println("It's a tie!");
    }

    /* Returns if the game is over, all pieces have been collected.
     * @return true if game over, false if pieces remaining
     */
    public static boolean gameOver(Board b){
        return b.getPot(true) + b.getPot(false) == b.getTotalPieces();
    }

    /* Returns if the game has already been won, if a player has over
     * half of the pieces. Determines the winner earlier than a full game.
     * @return true if a player has half the pieces
     */
    public static boolean gameOverHalf(Board b){
        int winningAmount = b.getTotalPieces() / 2;
        if(b.getPot(true) > winningAmount || b.getPot(false) > winningAmount)
            return true;
        else 
            return false;
    }

    /* Returns the user that is currently winning
     * @return 1 for p1, 2 for p2, 0 for tie
     */
    public static int whoWinning(Board b){
        int p1 = b.getPot(true);
        int p2 = b.getPot(false);

        if(p1 == p2)
            return 0; //Tie
        else if(p1 > p2)
            return 1; //Player 1 winning
        else
            return 2; //Player 2 winning
    }

    public static void printPos(Board b, boolean player){
        System.out.print("  ");

        if(player) //Player 1, pits are left-to-right
            for(int i = b.getNumPits(); i > 0; i--)
                System.out.print(" ("+i+")");

        else //Player 2, pits are right-to-left
            for(int i = 1; i <= b.getNumPits(); i++)
                System.out.print(" ("+i+")");

        System.out.println();
    }
}
