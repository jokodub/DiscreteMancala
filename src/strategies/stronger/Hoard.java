/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * 
 * Hoard Strategy
 * Prefers to keep large clumps of pieces as long as it can
 */

package strategies.stronger;
import java.util.ArrayList;
import java.util.Random;

import strategies.Strategy;
import structure.Board;

public class Hoard implements Strategy {

    private Random rand;

    public Hoard(){
        rand = new Random();
    }

    public int chooseMove(Board theBoard, boolean player){
        
        //Aiming for low variance to get most equal spread in data
        int[] moves = theBoard.getMoves(player);

        ArrayList<Integer> varPos = new ArrayList<>();
        double biggestVar = Double.MIN_VALUE;
        double tempVar;

        //Simulate a move and compare the result's variance
        for(int i = 0; i < moves.length; i++){
            Board copy = theBoard.copy();
            copy.move(player, moves[i]);
            tempVar = variance(copy.getMySide(player));

            if(tempVar < biggestVar)
                continue;
            else if(tempVar == biggestVar)
                varPos.add(moves[i]);
            else{ //New largest variance possible
                varPos.clear();
                varPos.add(moves[i]);
                biggestVar = tempVar;
            }

        }

        if(varPos.size() > 0)
            return varPos.get(rand.nextInt(varPos.size()));
        else
            return moves[rand.nextInt(moves.length)]; 
        
    }

    //Calculates the variance of pieces in a lane
    public double variance(int[] lane){
        // Var(x) = E(x^2) - E(x)^2
        double squaredMean = 0;
        double mean = 0;

        // All pits have equal probability
        for(int i = 0; i < lane.length; i++){
            squaredMean += (double)(lane[i] * lane[i]) / lane.length; //E(x^2) = sum( x^2*p(x) )
            mean += (double)lane[i] / lane.length; //E(x) = sum( x*p(x) )
        }
        double meanSquared = mean*mean;

        return squaredMean - meanSquared;
    }

    public String toString(){
        return "Hoard";
    }

}
 