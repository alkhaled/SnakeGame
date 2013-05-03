import java.awt.*;

public class Animator implements Runnable{
    
    private boolean running;
    private SnakeBoard component;
    private int delay = 70;
    
    public Animator ( SnakeBoard component){
	this.component = component;
    }

    public synchronized void run(){

	try{
	    while (true){
		if(running){
		    wait(delay);
		}
		else {
		    wait();
		}
		component.moveSnake();
		component.repaint();
	    }
	}
	catch ( InterruptedException e) {
	}
    }
     public synchronized void start(){
	running = true;
	notify();
    }
    public synchronized void stop(){
	running = false;
    }
}
