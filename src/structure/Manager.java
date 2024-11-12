/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * 
 * Manager
 * Tells Referee which games to play, and compiles results together.
 */

package structure;
import strategies.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Manager {
    
    public static void main(String[] args) {

        //Referee.play(new Board(), new Randomized(), new Randomized(), true, false); //Play single game
        simulate(); //Sim every Strategy

    }

    public static void simulate(){

        Strategy[] allStrategies = {new Randomized(), 
                                    new CloseFar(), 
                                    new FarClose(), 
                                    new Biggest(),
                                    new Smallest()};

        Board b = new Board(); //Board(initial pieces, pits per player)
        int totalPieces = b.getTotalPieces();
        
        PrintWriter writer;
        try{
            writer = new PrintWriter("out/simOutput.txt", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException f) {
            System.err.println("Error: Could not open output file");
            return;
        }
        
        
        /* Simulate games.
         * For each pair of strategies, run 100 games. Record the average scores across all games.
         * Also record the best and worst games for each player. 
         */
        for(int p1 = 0; p1 < allStrategies.length; p1++)
            for(int p2 = 0; p2 < allStrategies.length; p2++)
            {
                int runningP1Total = 0;
                int bestP1 = 0, worstP1 = Integer.MAX_VALUE;
                int P1score;

                //Run 100 games
                for(int i = 0; i < 100; i++)
                {
                    P1score = Referee.play(b.copy(), allStrategies[p1], allStrategies[p2], false, false);

                    runningP1Total += P1score; //Add this game to running total

                    if(P1score > bestP1) bestP1 = P1score;
                    if(P1score < worstP1) worstP1 = P1score;
                }
                int P1avg = runningP1Total /= 100; //Average out over 100
                int P2avg = totalPieces - P1avg;

                //Save summary
                writer.println("Matchup "+ (allStrategies.length*p1 + p2 + 1) +", "+allStrategies[p1].toString()+" vs "+allStrategies[p2].toString()+":");
                writer.println("  Average: "+allStrategies[p1].toString()+" "+P1avg+"-"+P2avg+" "+allStrategies[p2].toString());
                writer.println("  "+allStrategies[p1].toString()+"'s Best Game: "+bestP1+"-"+(totalPieces-bestP1));
                writer.println("  "+allStrategies[p1].toString()+"'s Worst Game: "+worstP1+"-"+(totalPieces-worstP1));
                writer.println();
            }
        
        writer.close();
    }
    
}
