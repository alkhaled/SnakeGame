import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

class SnakePanel extends JPanel implements ActionListener{

    private SnakeBoard  board;
    private JButton     restartButton, ghostButton;
    public  JLabel      score;

    private class Button extends JPanel{
	Button(){	     
	    setLayout (new BoxLayout (this, BoxLayout.X_AXIS));
	    restartButton = new JButton ("Start/Restart");
	    add(restartButton);
	    add(Box.createHorizontalStrut(25));
	    setBorder( new EmptyBorder(50,0,0,0));
	    restartButton.setEnabled(true);
	    restartButton.setFocusable(false);
	    ghostButton = new JButton ("Ghost");
	    add(ghostButton);
	    add(Box.createHorizontalStrut(25));
	    setBorder( new EmptyBorder(50,0,0,0));
	    ghostButton.setEnabled(true);
	    ghostButton.setFocusable(false); 
	}
    }

    public SnakePanel(){
	super();
	setLayout (new BoxLayout(this, BoxLayout.Y_AXIS));
	add (score = new JLabel());
	score.setFont(new Font ("Serif", Font.BOLD, 20));
	score.setForeground(Color.BLUE);
	score.setAlignmentX(0.5f);	  
	add ( board = new SnakeBoard(this));
	add(new Button());	
	restartButton.addActionListener(this);
	ghostButton.addActionListener(this);
	setBorder( new EmptyBorder(50,50,50,50));
    }

    public void actionPerformed (ActionEvent e){
	if (e.getSource().equals(restartButton)){
	    board.gameStart();
	}
	if (e.getSource().equals(ghostButton)){
	    board.ghost = !board.ghost;
	    board.setGhost();
	}
    }
}