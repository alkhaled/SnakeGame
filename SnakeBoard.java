import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

 

public class SnakeBoard extends JPanel{

 

    private static int boardWidth;
    private static int boardHeight;
    private static int borderSize;

   

    public SnakeDLL snake ;
    private Animator animator;
    private SnakePanel parent;

 

    // Defining the motion of the snake, if the boolean is true the snkae  moves in that position 

    public boolean up = false;
    public boolean dn = false;
    public boolean ryt= false;
    public boolean lft = false;

 
    //variables for food
    
    private int foodY;
    private int foodX;
    private BufferedImage foodImage;
    private BufferedImage ghostImage;

    // ghost current and desired position
    
    private int ghostY;
    private int ghostX;
    private int desiredX;
    private int desiredY;

 

    int score = 0;
    int size; // number of snake segments
   
    boolean game = true;// is the game running?
    public boolean ghost;// does the user want a ghost
 

    public SnakeBoard ( SnakePanel c){
	
          

            parent = c;
           
            animator = new Animator(this);
            (new Thread(animator)).start();

            
            setBackground    (Color.white);
            setMaximumSize   ( new Dimension ( 400,400));
            setMinimumSize   ( new Dimension ( 400,400));
            setPreferredSize ( new Dimension ( 400,400));
	    
            images();//load images
               
            addKeyListener   ( new MyKeyListener());
           
            setFocusable(true);
            requestFocusInWindow();
            setVisible(true);
           
	    gameStart();
	    
    }
    
 
    
    public void images(){

	try{
	    foodImage = ImageIO.read(new File("food.png"));
	}
	catch (IOException e ) {
	} 
	try{
	    ghostImage = ImageIO.read(new File("ghost.png"));
	}
	catch (IOException e ) {
	}  
    }
 
    
    public void gameStart(){
	
	game = true;
	score = 0;
	size = 1;
	setSnake();
	setFood();
	if (ghost) setGhost();
	//requestFocusInWindow();
	animator.start();
	
    }
    
    
    public synchronized void setGhost(){
	ghostX = 270;
	ghostY = 270;
    }


        public synchronized void setSnake(){ 
	
	//set the snakes initial position
	
	snake = new SnakeDLL ();
	for (int i = 150; i > 130; i-=10) {
	    snake.addFirst(150,i);	
	}
    }

    
    
    public synchronized void setFood(){
	
	// randomly place food
	
	foodX = (int)(Math.random()*300);
	foodY = (int)(Math.random()*300);
	
	
	SnakeDLL.Cell p =snake.header.next;
	while( p != snake.header){
	    if (p.x == foodX && p.y == foodY) {
		setFood();
		return;
	    }	  
	    p = p.next;
		
	}
	
    }

    
    public synchronized void moveGhost(){
	
	// if ghost is within a certain range of the snake atack head on, if not position the ghost between the snake and the food
	
	if ((((snake.header.next.x - foodX) < 130)  && ((snake.header.next.x - foodX) > -130 ))
	    && (((snake.header.next.y - foodY)< 130) && ((snake.header.next.y - foodY)> -130))){
	     
	    
	    desiredY = snake.header.next.y;
	    desiredX = snake.header.next.x;
	    
	    // if close enough snake is attacked from the side
	    if ( ryt || lft){
		if ( ghostY < desiredY) ghostY+=5;
		else  ghostY -=5;
	    }
	    
	    
	    if ( dn || up){
		
		if ( ghostX > desiredX) ghostX -=5;
		else  ghostX +=5;
	    }
	    
	}else {
	    
	    // if farther away stay between the snake and the food
	    desiredY = (int)(((snake.header.next.y + foodY)/2));
	    
	    desiredX = (int)(((snake.header.next.x + foodX)/2));
	    
	    if ( up || dn){
		
		if (ghostY < desiredY ) ghostY+=5;
		else  ghostY -=5;
		
	    }
	    if ( ryt || lft){
		
		if ( (ghostX < desiredX)) ghostX +=5;
		else  ghostX -=5;
		
		
	    }
	}
    }
    
    public synchronized void incrementSnake(){//s
	
	int newX = snake.header.next.x;
	int newY = snake.header.next.y;
	
	if (lft){
	    newX= snake.header.next.x - 10;
	    snake.addFirst( newX , newY);
	}
	
	else if (ryt){
	    newX= snake.header.next.x + 10;
	    snake.addFirst( newX , newY);
	}
	
	else if (up){
	    newY= snake.header.next.y - 10;
	    snake.addFirst( newX , newY);
	}
	
	else if (dn) {
	    newY= snake.header.next.y + 10;
	    snake.addFirst( newX , newY);
	}
	
    }

    public synchronized void moveSnake(){
	
	int newX = snake.header.next.x;
	int newY = snake.header.next.y;
	
	if (lft){
	    newX= snake.header.next.x - 10;
	    snake.addFirst( newX , newY);
	    snake.removeLast();
	}
	
	else if (ryt){
	    newX= snake.header.next.x + 10;
	    snake.addFirst( newX , newY);
	    snake.removeLast();
	}
	
	else if (up){
	    newY= snake.header.next.y - 10;
	    snake.addFirst( newX , newY);
	    snake.removeLast();
	}
	
	else if (dn) {
	    newY= snake.header.next.y + 10;
	    snake.addFirst( newX , newY);
	    snake.removeLast();
	}
		
	collisionDetect();
   
	if (ghost) moveGhost();		       		
    }

    public synchronized void collisionDetect(){//s

	//check if snake eats food
	// is point snake.x,snake.y within x , y of foodX,foodY

	if (((snake.header.next.x - foodX) < 10)  && ((snake.header.next.x - foodX) > -10 ) && ((snake.header.next.y - foodY)< 10) && ((snake.header.next.y - foodY)> -10)){
	   
	    size++;
	    score += 2*(size);
	    incrementSnake();
	    setFood();// set new food.	    
	}
       	
	//check if snake eats itself or is eaten by ghost
	
	SnakeDLL.Cell p =snake.header.next.next;
	while( p != snake.header){
	    
	    if (p.x == snake.header.next.x && p.y == snake.header.next.y){
		game = false;
	    }
	    
	    if ((ghost) && ((snake.header.next.x - ghostX) < 25)  && ((snake.header.next.x - ghostX) > -25 ) && ((snake.header.next.y - ghostY)< 25) && ((snake.header.next.y - ghostY)> -25)){
		
		snake.removeLast();
		if ( size > 0)	--size;
	    }
	    
	    p = p.next;
	    }
	
        
	// check if snakehead  meets ghost
	
	if ((ghost) && ((snake.header.next.x - ghostX) < 10)  && ((snake.header.next.x - ghostX) > -10 ) && ((snake.header.next.y - ghostY)< 10) && ((snake.header.next.y - ghostY)> -10))game = false;
	

	// check if snake falls of board
	
	if ( snake.header.next.x < 0 || snake.header.next.x > 400)game = false;
	if ( snake.header.next.y < 0 || snake.header.next.y > 400)game = false;
        	        
    }        

    public synchronized void paintComponent (Graphics g){
	
	super.paintComponent(g);

	// drawing food
	int scale = 10;
	g.drawImage(foodImage, foodX,foodY,13,13,this);            
	    
	//drawing ghost
	if (ghost)g.drawImage(ghostImage, ghostX,ghostY,13,13,this); 
	//	g.setColor(Color.black);
	//g.fillOval(ghostX,ghostY, scale, scale);
	    
	//drawing snake
	SnakeDLL.Cell p =snake.header.next;
	while( p != snake.header){
	    g.setColor(Color.green);
	    g.fillOval(p.x, p.y,scale , scale);
	    p = p.next;
	}
	parent.score.setText("Score:" + score + " ");
	    
	
	if (!game){  
	    animator.stop();
	    
	}
	
	
	
    }
    
    private class MyKeyListener extends KeyAdapter{
	
	public void keyPressed (KeyEvent e){
               
	    if(!game) {
		gameStart();
	    
		up  = false;
		dn  = false;
		lft = false;
		ryt = false;
	    }
	    else {
		    
		int key = e.getKeyCode();
                    
		if ( up == false && (key == KeyEvent.VK_DOWN)){
		    lft = false;
		    ryt = false;
		    dn  = true;
		}
                    
                
		else if ( dn == false && (key == KeyEvent.VK_UP)){
		    lft = false;
		    ryt = false;
		    up  = true;
		}
                
		else if ( lft == false && (key == KeyEvent.VK_RIGHT)){
		    up  = false;
		    ryt = true;
		    dn  = false;
		}
                    
		else if ( ryt == false && (key == KeyEvent.VK_LEFT)){
		    lft = true;
		    up = false;
		    dn  = false;
		}                 
	    }
	}
    }
}
