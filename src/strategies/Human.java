/* Discrete Math Mancala Project
 * John Vezzola
 * for Dr. Arup Guha
 * Human strategy
 * Allows a user to play the game, either
 * with another Human or against a Strategy.
 */

package DiscreteMancala;

import java.util.Scanner;

public class Human implements Strategy{
    
    public int chooseMove(Board theBoard){

        Scanner scan = new Scanner(System.in);
    
        System.out.println("Choose a pit: ");
        int temp = scan.nextInt();

        scan.close();
        return temp;
    }

}
