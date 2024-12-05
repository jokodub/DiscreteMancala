/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * 
 * Manager
 * Tells Referee which games to play, and compiles results together.
 */

package structure;
import strategies.*;
import strategies.simple.*;
import strategies.stronger.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Manager {
    
    public static void main(String[] args) {

        Referee.play(new Board(), new Randomized(), new Hoard(), true, false); //Play single game
        //simulate(10000, 4, 6); //Sim every Strategy

    }

    /* Simulate games between every Strategy (trials) times
    * For each pair of strategies, run amount of games. Record the average scores across all games.
    * Also record the best and worst games for each player. 
    * @param trials as number of games to run per matchup
    * @param pieces as number of pieces each pit starts with
    * @param pits as number of pits per lane
    */
    public static void simulate(int trials, int pieces, int pits){

        //Place all Strategies to be tested in here
        Strategy[] allStrategies = {new Randomized(), 
                                    new CloseFar(), 
                                    new FarClose(), 
                                    new Biggest(),
                                    new Smallest(),
                                    new Bonus(),
                                    new Capture(),
                                    new Spread(),
                                    new Hoard()
                                    };

        Board b = new Board(pieces, pits);
        int totalPieces = b.getTotalPieces();
        
        PrintWriter writer = null;
        try{
            writer = new PrintWriter("out/simOutput.txt", "UTF-8");

            //Simulate every possible pair of Strategies, once as each player
            for(int p1 = 0; p1 < allStrategies.length; p1++)
                for(int p2 = 0; p2 < allStrategies.length; p2++)
                {
                    int runningP1Total = 0;
                    int bestP1 = 0, worstP1 = Integer.MAX_VALUE;
                    int P1score;

                    //Play however many games
                    for(int i = 0; i < trials; i++)
                    {
                        P1score = Referee.play(b.copy(), allStrategies[p1], allStrategies[p2], false, false);

                        runningP1Total += P1score; //Add this game to running total

                        if(P1score > bestP1) bestP1 = P1score;
                        if(P1score < worstP1) worstP1 = P1score;
                    }
                    int P1avg = runningP1Total /= trials; //Average out over all trials
                    int P2avg = totalPieces - P1avg;

                    //Save summary
                    writer.println("Matchup "+ (allStrategies.length*p1 + p2 + 1) +", "+allStrategies[p1].toString()+" vs "+allStrategies[p2].toString()+":");
                    writer.println("  Average: "+allStrategies[p1].toString()+" "+P1avg+"-"+P2avg+" "+allStrategies[p2].toString());
                    writer.println("  "+allStrategies[p1].toString()+"'s Best Game: "+bestP1+"-"+(totalPieces-bestP1));
                    writer.println("  "+allStrategies[p1].toString()+"'s Worst Game: "+worstP1+"-"+(totalPieces-worstP1));
                    writer.println();
                }
        
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException f) {
            System.err.println("Error: Could not open output file");
        }//End try-catch
        
    }
    
}
