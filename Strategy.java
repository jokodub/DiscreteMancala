/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * Strategy interface
 * Requires all players to implement a move 
 * method, which is all that MancalaPlayer
 * requires.
 */

package DiscreteMancala;

public interface Strategy {
    
    /* Chooses a pit position to Board.move()
     * @param theBoard as the beginning board state. Can be recursive but must finally return:
     * @return an integer between [1,pits]
     */
    public int chooseMove(Board theBoard);
}
