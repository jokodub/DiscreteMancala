/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * 
 * Spread Strategy
 * Prefers to spread its pieces out, minimizing empty pits.
 */

package strategies.stronger;
import strategies.Strategy;
import structure.Board;

public class Spread implements Strategy {

    public int chooseMove(Board theBoard, boolean player){
        
        //calc variance of each pit, if over threshold mark that one to move
    }

    public String toString(){
        return "Spread";
    }

}
 
