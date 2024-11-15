/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * 
 * Capture Strategy
 * Captures whenever it can. When multiple captures are
 * possible, choose the one that earns the most pieces.
 */

package strategies.stronger;
import strategies.Strategy;
import structure.Board;

import java.util.ArrayList;
import java.util.Random;

public class Capture implements Strategy {

    private Random rand;

    public Capture(){
        rand = new Random();
    }

    public int chooseMove(Board theBoard, boolean player){

        int[] moves = theBoard.getMoves(player);
        ArrayList<Integer> capturePos = new ArrayList<>();

        int startingPot = theBoard.getPot(player); //Compare with pot after move
        int maxCapture = 0; //Amount the largest capture earns
        int tempCapture;

        for(int i = 0; i < moves.length; i++){
            
            //Create new copy for each move
            tempCapture = 0;
            Board copy = theBoard.copy();

            //Move on a copy of the board, remaking the capture logic would be a pain 
            int status = copy.move(player, moves[i]);
            if((status & 4) == 4)
                tempCapture = copy.getPot(player) - startingPot;

            if(tempCapture == 0) //Skip non-capture moves
                continue;
            else if(tempCapture == maxCapture) //Keep track of duplicate capture amounts
                capturePos.add(moves[i]);
            else{ //Larger capture than recorded
                capturePos.clear(); //Clear out old values
                capturePos.add(moves[i]);
                maxCapture = tempCapture;
            }
        }

        if(capturePos.size() > 0)
            return capturePos.get(rand.nextInt(capturePos.size())); //Return a random max capture
        else
            return moves[rand.nextInt(moves.length)]; //Random move if none are captures

    }

    public String toString(){
        return "Capture";
    }

}
 