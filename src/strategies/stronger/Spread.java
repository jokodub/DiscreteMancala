/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * 
 * Spread Strategy
 * Prefers to spread its pieces out, minimizing empty pits.
 */

package strategies.stronger;
import java.util.ArrayList;
import java.util.Random;

import strategies.Strategy;
import structure.Board;

public class Spread implements Strategy {

    private Random rand;

    public Spread(){
        rand = new Random();
    }

    public int chooseMove(Board theBoard, boolean player){
        
        //Aiming for low variance to get most equal spread in data
        int[] moves = theBoard.getMoves(player);

        ArrayList<Integer> varPos = new ArrayList<>();
        double lowestVar = Double.MAX_VALUE;
        double tempVar;

        //Simulate a move and compare the result's variance
        for(int i = 0; i < moves.length; i++){
            Board copy = theBoard.copy();
            copy.move(player, moves[i]);
            tempVar = variance(copy.getMySide(player));

            if(tempVar > lowestVar)
                continue;
            else if(tempVar == lowestVar)
                varPos.add(moves[i]);
            else{ //New smallest variance possible
                varPos.clear();
                varPos.add(moves[i]);
                lowestVar = tempVar;
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
        return "Spread";
    }

}
 
