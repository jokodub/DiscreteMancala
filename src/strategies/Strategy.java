/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * Strategy interface
 * Requires all players to implement a move 
 * method, which is all that MancalaPlayer
 * requires.
 */

package strategies;
import structure.Board;

/* All strategies for players
 * Human = Prompts stdin
 * 
 * ---Greedy---
 * Looks no further than the current state of the board
 * 
 * Randomized = chooses a random pit 
 * FarClose = chooses the farthest pit from pot it can
 * CloseFar = chooses the closest pit from pot it can
 * Biggest = chooses the largest pit, randomly if equal
 * Smallest = chooses the smallest pit, randomly if equal
 * 
 * ---Minimax Weights---
 * Different strategies that minimax will prioritize 
 * 
 * Most empty
 * Captures
 * Most spread out (all 1s vs one 6)
 * 
 */

public interface Strategy {
    
    /* Chooses a pit position to Board.move()
     * @param theBoard as the beginning board state. Can be recursive but must finally return:
     * @return an integer between [1,pits]
     */
    public int chooseMove(Board theBoard, boolean player);
}
