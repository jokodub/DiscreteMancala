/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * Board class
 * A board is really an array of Pits, with 
 * methods to move and access them.
 */

package DiscreteMancala;

public class Board {
    
    //Instance variables
    private int initialPieces; //# pieces in each pit to begin
    private int pits; //# of spaces in a lane
    private Pit[] boardarr; //arr to hold entire board

    //Constructors and Helpers

    public Board(){
        initialPieces = 4;
        pits = 6;
        boardarr = setBoardArr(initialPieces, pits);
    }

    public Board(int numpieces, int numpits){
        initialPieces = numpieces;
        pits = numpits;
        boardarr = setBoardArr(initialPieces, pits);
    }

    public Board(int[] state){ //Load a boardstate
        initialPieces = 0;
        pits = (state.length - 2) / 2;
        boardarr = setBoardArr(state);
    }

    /* Creates the Pit[] that will be used for board simulation
     * @param numpieces as inital amount in each pit
     * @param numpits as number of pits in a lane
     * @return a Pit[] that contains the starting board setup
     */
    public Pit[] setBoardArr(int numpieces, int numpits){
        int totalSpaces = (numpits * 2) + 2; //2 lanes + 2 pots
        Pit[] board = new Pit[totalSpaces];

        //Create all the pits, first half to user, second half to opponent
        for(int i = 0; i < totalSpaces / 2 - 1; i++)
            board[i] = new Pit(numpieces, true);
        board[totalSpaces / 2 - 1] = new Pit(true); //User's pot

        for(int i = totalSpaces / 2; i < totalSpaces - 1; i++)
            board[i] = new Pit(numpieces, false);
        board[totalSpaces - 1] = new Pit(false); //Opponent's pot
        
        return board;
    }

    /* Alternatively, pass an array of each pit's pieces
     * @param state as the values of each pit, including pots
     */
    public Pit[] setBoardArr(int[] state){
        if(state.length < 4 || state.length % 2 != 0){ //Minimum size is 4 and cannot be odd
            System.err.println("Error: Given boardstate array in setBoard is impossible length");
            return null;
        }
            
        int totalSpaces = state.length;
        Pit[] board = new Pit[totalSpaces];

        //Create all the pits, first half to user, second half to opponent
        for(int i = 0; i < totalSpaces / 2 - 1; i++)
            board[i] = new Pit(state[i], true);

        board[totalSpaces / 2 - 1] = new Pit(true); //User's pot
        board[totalSpaces / 2 - 1].setPieces(state[totalSpaces / 2 - 1]); 

        for(int i = totalSpaces / 2; i < totalSpaces - 1; i++)
            board[i] = new Pit(state[i], false);

        board[totalSpaces - 1] = new Pit(false); //Opponent's pot
        board[totalSpaces - 1].setPieces(state[totalSpaces - 1]); 

        return board;
    }

    //Getters and Setters

    /* Copies the pieces in each pit to an int[]
     * @return an int[] that can be used by setBoardArr
     */
    public int[] getBoardArr(){
        int[] copy = new int[boardarr.length];

        for(int i = 0; i < boardarr.length; i++)
            copy[i] = boardarr[i].getPieces();

        return copy;
    }

    public int getInitialPieces() { return initialPieces; }
    public int getNumPits() { return pits; }
    public int getPieces(int index) { return boardarr[index].getPieces(); }

    //Methods

    /* Returns the index in boardarr that this player's pot is in
     * @param player is true for user, false for opponent
     * @return an index into boardarr
     */
    public int getPotIndex(boolean player){
        return player ? pits : (pits * 2) + 1; //Pots are at the end of each player's lane, length of pits.
    }

    /* Returns the index of a given pit in the boardarr
     *   12 11 10 9  8  7
     * 13
     *                   6
     *   0  1  2  3  4  5
     * @param player true for user, false for opponent
     * @param pos as the pit in question
     * @return an index into boardarr
     */
    public int getIndex(boolean player, int pos){
        int offset = player ? 0 : pits + 1; //Player 1 starts at 0, Player 2 starts after 1's pot
        return offset + pos - 1;
    }

    /* Returns the index of the opponent's pit adjacent to pos, for capture
     * @param player as who the current player is, opponent is !player
     * @param pos as the numbering of player's pit
     * @return an index into opponent's side of boardarr
     */
    public int getOpposingIndex(boolean player, int pos){
        int offset = player ? pos - 1 : pos + pits; //Changes position to an index
        return 2 * pits - offset;
    }

    /* Makes a mancala move by taking all the pieces from a pit and moving them
     * one at a time across the board.
     *    6 5 4 3 2 1
     *  x             
     *                x
     *    1 2 3 4 5 6
     * @param player as which side of the board has the turn
     * @param pos as a number [1-pits] inclusive indicating which pit to move
     * @return -1 for illegal moves, 0 for success, 1 for bonus move
     */
    public int move(boolean player, int pos){
        if (!(pos > 0 && pos <= pits)) {
            System.err.println("Error: Attempting to move on a pit that doesn't exist");
            return -1;
        }

        int startPos = getIndex(player, pos);

        if(boardarr[startPos].isEmpty()) return -1; //Cannot move an empty pit

        /* Rules of moving:
         * Take pieces out of the pit to move
         * Move along boardarr, skipping opponent's pot
         * If move finishes on an empty space on your side, capture opposing side's pit
         * If the final piece is placed in your pot, move again.
         */
        int currPos = startPos;
        int bonus = 0; //By default, no extra moves

        for(int hand = boardarr[getIndex(player, pos)].take(); hand > 0; hand--){

            //Increment postition, loop around edge
            currPos += 1;
            if(currPos > pits * 2 + 1) currPos = 0;

            //Skip over opponent's pot, undo hand decrement
            if(currPos == getPotIndex(!player)){
                hand++;
                continue;
            }

            boardarr[currPos].place(); //Place a piece

            //If last piece in hand, check for bonuses
            if(hand == 1){

                //If final piece lands in player's pot, another turn
                if(currPos == getPotIndex(player)){ 
                    bonus = 1; //Player gets a bonus move
                }

                //Capture rules
                if(boardarr[currPos].getPieces() == 1 && //Pit was empty
                currPos >= getIndex(player, 1) && //Pit was on your side 
                currPos <= getIndex(player, pits) &&
                !boardarr[getOpposingIndex(player, currPos)].isEmpty()){ //Opponent's pit has something
                    //Place both your and opponent's pits in your pot
                    boardarr[getPotIndex(player)].place(boardarr[getOpposingIndex(player, currPos)].take() + boardarr[currPos].take());
                }
            }
        } //End forloop moving pieces

        return bonus; //Return 0 or 1 if earned an extra move
    }

    /* Turns board into a unique number string
     * "initialpieces,numpits,pit1,pit2...,opppot"
     * @param b as Board to encode
     * @return the encoded String delimited by commas
     */
    public static String encode(Board b){
        StringBuilder sb = new StringBuilder();
        sb.append(b.getInitialPieces() + "," + b.getNumPits());

        //Append all the pit values
        int[] pitArr = b.getBoardArr();
        for(int i = 0; i < pitArr.length; i++)
            sb.append("," + pitArr[i]);

        return sb.toString();
    }

    /* Reverts encoded board back into Board
     * @param code as encoded Board as String with comma delimiters
     * @return the Board it represents
     */
    public static Board decode(String code){
        String[] codeArr = code.split(","); //Split apart

        //Parse pits back into integer amounts
        int[] pitArr = new int[Integer.parseInt(codeArr[1]) * 2 + 2]; //Arr to store amounts, 2nd arg is numpits
        for(int i = 2; i < pitArr.length; i++)
            pitArr[i] = Integer.parseInt(codeArr[i]);

        return new Board(pitArr);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        //Opponent's side of board
        sb.append("  ");
        for(int i = pits*2; i > pits+1; i--)
            sb.append(String.format(" %2d", boardarr[i].getPieces()));
        sb.append("\n");

        //Opp pot
        sb.append(String.format("%2d\n", boardarr[pits*2 + 1].getPieces())); 
        
        //User's pot 
        sb.append("  ");
        for(int i = 0; i < pits; i++)
            sb.append("   ");
        sb.append(String.format("%2d\n", boardarr[pits].getPieces()));

        //User's side of board
        sb.append("    ");
        for(int i = pits*2; i > pits+1; i--)
            sb.append(String.format(" %2d", boardarr[i].getPieces()));
        sb.append("\n");

        return sb.toString();
    }

}
