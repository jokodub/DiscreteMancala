/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * 
 * FarClose Strategy
 * Chooses the pit farthest from the pot
 */

package strategies.simple;
import strategies.Strategy;
import structure.Board;

public class FarClose implements Strategy {

    public int chooseMove(Board theBoard, boolean player) {
        int[] moves = theBoard.getMoves(player);
        return moves[0]; //Positions are sorted descending, so first element is farthest.
    }

    public String toString(){
        return "FarClose";
    }

}
