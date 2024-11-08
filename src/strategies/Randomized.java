/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * 
 * Randomized Strategy
 * Chooses a random pit.
 */

package strategies;
import structure.Board;

import java.util.Random;

public class Randomized implements Strategy{

    private Random rand;

    public Randomized(){
        rand = new Random();
    }

    public int chooseMove(Board theBoard, boolean player) {

        int[] moves = theBoard.getMoves(player);

        return moves[rand.nextInt(moves.length)]; //Return a random option from possible moves
    }

}