/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * 
 * Bonus Strategy
 * Prioritizes getting bonus turns over any factor. 
 * Chooses the bonus closest to the pot if multiple are
 * available, and randomly if none are.
 */

package strategies.stronger;
import strategies.Strategy;
import structure.Board;

import java.util.ArrayList;
import java.util.Random;

public class Bonus implements Strategy {

    private Random rand;

    public Bonus(){
        rand = new Random();
    }

    public int chooseMove(Board theBoard, boolean player){

        int[] moves = theBoard.getMoves(player);
        ArrayList<Integer> bonusPos = new ArrayList<>();

        //If number of pieces matches the position number (mod full loop around), results in bonus
        for(int i = 0; i < moves.length; i++){
            if(moves[i] == theBoard.getPieces(player, moves[i]) % (theBoard.getTotalPits()-1))
                bonusPos.add(moves[i]);
        }

        if(bonusPos.size() > 0)
            return bonusPos.get(bonusPos.size()-1); //Positions are descending, so last is closest to pit
        else
            return moves[rand.nextInt(moves.length)]; //Random move if none are bonuses
        
    }

    public String toString(){
        return "Bonus";
    }

}
 
