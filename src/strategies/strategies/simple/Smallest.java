/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * 
 * Smallest Strategy
 * Choose the pit with the least pieces.
 * If more than one holds the minimum, choose at random.
 */

package strategies.simple;
import structure.Board;

import java.util.ArrayList;
import java.util.Random;

import strategies.Strategy;

public class Smallest implements Strategy {

    private Random rand;

    public Smallest(){
        rand = new Random();
    }

    public int chooseMove(Board theBoard, boolean player){

        int[] moves = theBoard.getMoves(player);
        ArrayList<Integer> minPos = new ArrayList<>(); //Holds all positions that contain the min.

        //Find the maximum pieces and how many contain it
        int minVal = theBoard.getTotalPieces();
        int tempPieces;

        for(int i = 0; i < moves.length; i++){

            tempPieces = theBoard.getPieces(player, moves[i]);

            if(tempPieces > minVal) //Don't consider less than largest
                continue; 
            else if(tempPieces == minVal) //Keep track of duplicate maximums
                minPos.add(moves[i]); 
            else { //New maximum
                minPos.clear(); //Clear out old values
                minPos.add(moves[i]);
                minVal = tempPieces;
            }
            
        }

        return minPos.get(rand.nextInt(minPos.size())); //Return a random choice of the maximums
    }

    public String toString(){
        return "Smallest";
    }
}
 