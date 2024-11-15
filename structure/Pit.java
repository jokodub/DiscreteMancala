/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * 
 * Pit
 * A pit is what mancala boards are made of, containing pieces 
 * that can be sowed around the board.
 * Pots are the large pits at the edges of the board that cannot 
 * be moved and act as the player's score. 
 */

package structure;

public class Pit {

    //Instance variables
    private int pieces; //Beads currently here
    private boolean owner; //True for user, false for opponent
    private boolean pot; //At end of lane, cannot be taken from

    //Constructors

    public Pit(int numpieces, boolean player){ //Default as a pit
        pieces = numpieces;
        owner = player;
        pot = false;
    }

    public Pit(boolean potOwner){ //For pots (large pits at the ends)
        pieces = 0;
        owner = potOwner;
        pot = true;
    }

    //Getters and Setters

    public int getPieces(){ return pieces; } //Identical to peek()
    public boolean getOwner(){ return owner; }
    public boolean isPot(){ return pot; }
    public void setPieces(int amount){ pieces = amount; }


    //Methods

    //Returns true if no pieces, false if any
    public boolean isEmpty(){
        return pieces > 0 ? false : true;
    }

    /* Removes all pieces from this pit to be placed elsewhere
     * @param none
     * @return the amount that was in the pit
     */
    public int take(){
        if(pot) { //Cannot take from pots, only pits.
            System.err.println("Error: Attempting to take() from a pot.");
            return 0;
        }

        int save = pieces;
        pieces = 0;
        return save;
    }

    // Put a single piece 
    public void place(){
        pieces += 1;
    }

    //Alternatively, place more than one. Used for pots when capturing opponent's pieces. 
    public void place(int amount){
        if(!pot) System.err.println("Error: Attempting to place() more than one bead in a pit");

        pieces += amount;
    }

}
