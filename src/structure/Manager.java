/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * 
 * Manager
 * Tells Referee which games to play, and compiles results together.
 */

package structure;
import strategies.*;

public class Manager {
    
    public static void main(String[] args) {

        Referee.play(new Human(), new Randomized(), true, false);

    }
    
    
}
