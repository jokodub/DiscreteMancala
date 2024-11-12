/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * 
 * Smallest Strategy
 * Choose the pit with the least pieces.
 * If more than one holds the minimum, choose at random.
 */

package strategies;
import structure.Board;

import java.util.Random;

public class Smallest implements Strategy {

    private Random rand;

    public Smallest(){
        rand = new Random();
    }

    public int chooseMove(Board theBoard, boolean player){

        int[] moves = theBoard.getMoves(player);

        //Find the minimum pieces and how many contain it
        int minVal = theBoard.getTotalPieces();
        int minCount = 0;
        int tempPieces;
        for(int i = 0; i < moves.length; i++){

            tempPieces = theBoard.getPieces(player, moves[i]);

            if(tempPieces > minVal) { continue; } //Don't consider more than smallest
            else if(tempPieces == minVal) { minCount++; } //Keep track of duplicate minimums
            else { //New minimum
                minVal = tempPieces;
                minCount = 1;
            }
            
        }

        //Only consider those that match minimum
        int[] desiredMoves = new int[minCount];
        for(int i = 0, j = 0; i < moves.length; i++)
            if(theBoard.getPieces(player, moves[i]) == minVal)
                desiredMoves[j++] = moves[i];

        return desiredMoves[rand.nextInt(desiredMoves.length)]; //Return a random choice of the minimums
    }

    public String toString(){
        return "Smallest";
    }
}
 