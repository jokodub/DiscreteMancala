/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * 
 * Biggest Strategy
 * Choose the pit with the most pieces.
 * If more than one holds the maximum, choose at random.
 */

package strategies.simple;
import structure.Board;

import java.util.Random;
import java.util.ArrayList;

import strategies.Strategy;

public class Biggest implements Strategy {

    private Random rand;

    public Biggest(){
        rand = new Random();
    }

    public int chooseMove(Board theBoard, boolean player){

        int[] moves = theBoard.getMoves(player);
        ArrayList<Integer> maxPos = new ArrayList<>(); //Holds all positions that contain the max.

        //Find the maximum pieces and how many contain it
        int maxVal = 0;
        int tempPieces;

        for(int i = 0; i < moves.length; i++){

            tempPieces = theBoard.getPieces(player, moves[i]);

            if(tempPieces < maxVal) //Don't consider less than largest
                continue; 
            else if(tempPieces == maxVal) //Keep track of duplicate maximums
                maxPos.add(moves[i]); 
            else { //New maximum
                maxPos.clear(); //Clear out old values
                maxPos.add(moves[i]);
                maxVal = tempPieces;
            }
            
        }
        
        return maxPos.get(rand.nextInt(maxPos.size())); //Return a random choice of the maximums
    }

    public String toString(){
        return "Biggest";
    }

}
 