/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * 
 * Human Strategy
 * Allows a user to play the game, either
 * with another Human or against a Strategy.
 */

package strategies;
import structure.Board;

import java.util.Scanner;

public class Human implements Strategy{

    private Scanner scan;

    public Human(){
        scan = new Scanner(System.in);
    }
    
    public int chooseMove(Board theBoard, boolean player){
        
        if(player == true)
            System.out.print("Player 1, ");
        else
            System.out.print("Player 2, ");

        System.out.print("Choose a pit: ");
        int temp = scan.nextInt();

        return temp;
    }

}
