/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * 
 * Board 
 * Contains an array of Pits, which the game is played on.
 */

package structure;

public class Board {
    
    //Instance variables
    private int initialPieces; //# pieces in each pit to begin
    private int totalPieces;
    private int pits; //# of spaces in a lane
    private Pit[] boardarr; //arr to hold entire board

    //Constructors and Helpers

    public Board(){
        initialPieces = 4;
        pits = 6;
        totalPieces = initialPieces * pits * 2;
        boardarr = setBoardArr(initialPieces, pits);
    }

    public Board(int numpieces, int numpits){
        initialPieces = numpieces;
        pits = numpits;
        totalPieces = initialPieces * pits * 2;
        boardarr = setBoardArr(initialPieces, pits);
    }

    public Board(int[] state){ //Load a boardstate
        initialPieces = 0;
        pits = (state.length - 2) / 2;
        totalPieces = 0;
        boardarr = setBoardArr(state); //Also sets totalPieces
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

        //Can't determine initial pieces, so set totalPieces here
        for(int i = 0; i < state.length; i++)
            totalPieces += state[i];
            
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
    public int getTotalPits() { return pits*2 + 2; }
    public int getTotalPieces() { return totalPieces; }
    public int getPieces(int index) { return boardarr[index].getPieces(); }
    public int getPieces(boolean player, int pos) { return boardarr[index(player, pos)].getPieces(); }
    public int getPot(boolean player) { return boardarr[potIndex(player)].getPieces(); }

    //Methods

    /* Returns the index of a given pit in the boardarr
     *   12 11 10 9  8  7
     * 13
     *                   6
     *   0  1  2  3  4  5
     * @param player true for user, false for opponent
     * @param pos as the pit in question
     * @return an index into boardarr
     */
    public int index(boolean player, int pos){
        int offset = player ? 0 : pits + 1; //Player 1 starts at 0, Player 2 starts after 1's pot
        return offset + (pits - pos);
    }

    // Inverse of index()
    public int position(int index){
        return pits - index % (pits+1); // Mod between [0,pits], return [pits,0] (0 being the pot)
    }

    // Returns the index in boardarr that this player's pot is in
    public int potIndex(boolean player){
        return player ? pits : (pits * 2) + 1; //Pots are at the end of each player's lane, length of pits.
    }

    // Returns the index of the opponent's pit adjacent to pos, for capture
    public int opposingIndex(boolean player, int index){
        return index(!player, pits - position(index) + 1);
    }

    // Return an array containing all legal moves
    public int[] getMoves(boolean player){

        int startIndex = index(player, pits);
        int endIndex = index(player, 1);

        //Find amount of moves available
        int amount = 0;
        for(int i = startIndex; i <= endIndex; i++)
            if(getPieces(i) != 0) amount++;
        
        //Write them into array
        int[] allMoves = new int[amount];
        for(int i = startIndex, m = 0; i <= endIndex; i++)
            if(getPieces(i) != 0) allMoves[m++] = position(i);
        
        return allMoves;
    }

    // Return an array of the amount of pieces in each pit
    public int[] getMySide(boolean player){

        int[] myPits = new int[pits];
        int startIndex = index(player, pits);
        int endIndex = index(player, 1);

        for(int i = startIndex, p = 0; i <= endIndex; i++)
            myPits[p++] = getPieces(i);

        return myPits;
    }

    //Return number of pieces on player's side of the board
    public int getLanePieces(boolean player){

        int lane = 0;
        int startIndex = index(player, pits);
        int endIndex = index(player, 1);

        for(int i = startIndex; i <= endIndex; i++)
            lane += getPieces(i);

        return lane;
    }

    //Player has lost, and all remaining pieces on the board go to opponent.
    public void forfeit(boolean player){

        int oppPot = potIndex(!player);
        int startIndex = index(!player, pits);
        int endIndex = index(!player, 1);

        for(int i = startIndex; i <= endIndex; i++)
            boardarr[oppPot].place(boardarr[i].take());
    }

    /* Makes a mancala move by taking all the pieces from a pit and moving them
     * one at a time across the board.
     *    1 2 3 4 5 6
     *  x             
     *                x
     *    6 5 4 3 2 1
     * @param player as which side of the board has the turn
     * @param pos as a number [1-pits] inclusive indicating which pit to move
     * @return an integer code, comprised of:
     *      +1 for legal
     *      +2 for bonus
     *      +4 for capture
     *      +8 for forfeit
     */
    public int move(boolean player, int pos){

        if (pos < 1 || pos > pits) {
            System.err.println("Error: Attempting to move on a pit that doesn't exist");
            return 0;
        }

        int startIndex = index(player, pos);

        if(getPieces(startIndex) == 0) {
            System.err.println("Error: Attempting to move an empty pit");
            return 0; 
        }
        
        /* Rules of moving:
         * Take pieces out of the pit to move
         * Move along boardarr, skipping opponent's pot
         * If move finishes on an empty space on your side, capture opposing side's pit
         * If the final piece is placed in your pot, move again.
         */
        int currIndex = startIndex;
        int status = 0b0001; //By default, +1 = legal

        for(int hand = boardarr[index(player, pos)].take(); hand > 0; hand--){

            //Increment postition, loop around edge
            currIndex += 1;
            if(currIndex > getTotalPits()-1) currIndex = 0;

            //Skip over opponent's pot, undo hand decrement
            if(currIndex == potIndex(!player)){
                hand++;
                continue;
            }

            boardarr[currIndex].place(); //Place a piece

            //If last piece in hand, check for bonuses
            if(hand == 1){

                //If final piece lands in player's pot, another turn
                if(currIndex == potIndex(player)){ 
                    status += 0b0010; //+2 = bonus
                }

                //Capture rules
                if(getPieces(currIndex) == 1 //Pit was empty
                   && currIndex >= index(player, pits) && currIndex <= index(player, 1) //Landed on your side
                   && getPieces(opposingIndex(player, currIndex)) > 0) //Opponent's pit has something
                { 
                    //Place both your and opponent's pits in your pot
                    boardarr[potIndex(player)].place(boardarr[opposingIndex(player, currIndex)].take() + boardarr[currIndex].take());
                    status += 0b0100; //+4 = capture
                }
            }
        } //End forloop moving pieces

        //If player's lane is now empty, forfeit game
        if(getLanePieces(player) == 0)
        {
            forfeit(player);
            status += 0b1000; //+8 = forfeit
        }

        return status; //Return encoded status of bonus, capture, and forfeit.
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
        for(int i = pits*2; i > pits; i--)
            sb.append(String.format(" %3d", boardarr[i].getPieces()));
        sb.append("\n");

        //Opp pot
        sb.append(String.format("%3d\n", boardarr[potIndex(false)].getPieces())); 
        
        //User's pot 
        sb.append("  ");
        for(int i = 0; i < pits; i++)
            sb.append("    ");
        sb.append(String.format("%3d\n", boardarr[potIndex(true)].getPieces()));

        //User's side of board
        sb.append("  ");
        for(int i = 0; i < pits; i++)
            sb.append(String.format(" %3d", boardarr[i].getPieces()));

        return sb.toString();
    }

    public Board copy(){
        return new Board(getBoardArr());
    }

}
