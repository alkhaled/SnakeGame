import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

class SnakeGame extends JFrame{

    SnakeGame(){
	super("SnakeGame");
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	setMaximumSize   ( new Dimension ( 700,700));
	setMinimumSize   ( new Dimension ( 700,700));
	setPreferredSize ( new Dimension ( 700,700));
	   
	setContentPane( new SnakePanel());
	pack();
	setResizable(false);
	setVisible(true);
    }
    public static void main (String[] args){
	new SnakeGame();
    }
}
