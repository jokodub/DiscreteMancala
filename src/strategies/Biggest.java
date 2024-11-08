/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * 
 * Biggest Strategy
 * Choose the pit with the most pieces.
 * If more than one holds the maximum, choose at random.
 */

package strategies;
import structure.Board;

import java.util.Random;

public class Biggest implements Strategy {

    private Random rand;

    public Biggest(){
        rand = new Random();
    }

    public int chooseMove(Board theBoard, boolean player){

        int[] moves = theBoard.getMoves(player);

        //Find the maximum pieces and how many contain it
        int maxVal = 0;
        int maxCount = 0;
        int tempPieces;
        for(int i = 0; i < moves.length; i++){

            tempPieces = theBoard.getPieces(player, moves[i]);

            if(tempPieces < maxVal) { continue; } //Don't consider less than largest
            else if(tempPieces == maxVal) { maxCount++; } //Keep track of duplicate maximums
            else { //New maximum
                maxVal = tempPieces;
                maxCount = 1;
            }
            
        }

        //Only consider those that match maximum
        int[] desiredMoves = new int[maxCount];
        for(int i = 0, j = 0; i < moves.length; i++)
            if(theBoard.getPieces(player, moves[i]) == maxVal)
                desiredMoves[j++] = moves[i];

        return desiredMoves[rand.nextInt(desiredMoves.length)]; //Return a random choice of the maximums
    }

}
 