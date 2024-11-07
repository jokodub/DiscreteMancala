package DiscreteMancala;

import java.util.Random;

public class Randomized implements Strategy{

    public int chooseMove(Board theBoard){
        Random rand = new Random();
        return rand.nextInt(theBoard.getNumPits()) + 1; //[1,pits] inclusive
    }

}