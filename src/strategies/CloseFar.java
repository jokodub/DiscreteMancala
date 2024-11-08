/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * 
 * CloseFar Strategy
 * Chooses the pit closest to the pot
 */

package strategies;
import structure.Board;

public class CloseFar implements Strategy {

    public int chooseMove(Board theBoard, boolean player) {
        int[] moves = theBoard.getMoves(player);
        return moves[moves.length-1]; //Positions are sorted descending, so last element is closest.
    }
}
