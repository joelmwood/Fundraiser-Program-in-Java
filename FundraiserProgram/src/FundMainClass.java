/**
  * @author Learning Team C
 */

import javax.swing.JFrame;

public class FundMainClass {

    public static void main(String[] args) {
       FundMainForm mainFrame = new FundMainForm(); 
       mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       mainFrame.setSize( 350, 400 );//set frame size
       mainFrame.setResizable(false);//keeps the user from resizing the window
       mainFrame.setVisible ( true );//display frame
    }//end main    
}//end class Retail Calculator
