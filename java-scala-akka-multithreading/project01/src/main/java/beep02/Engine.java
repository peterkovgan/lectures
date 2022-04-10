package beep02;



/**
@author Benjamin Romanoff
This class is a part of a java-scala-akka multithreading book samples
Use this code without any warranty.
Send feedback and propositions to java.scala.akka@gmail.com 
You allowed use this code or any part of it if you provide the link to
The original location of the code: 
https://github.com/beep02/java-scala-akka-multithreading
**/


/**
 * 
 * The engine moves the tank and repaints the field
 *
 */
public class Engine extends Thread{

	private Tank tank;
	private BattleField field;
	private long delay;
	private int step;
	
	
	public Engine(BattleField field, Tank tank, long delay, int step){
		this.tank  = tank;
		this.field = field;
		this.delay = delay;
		this.step  = step;
		
	}
	
	@Override
	public void run() {
		
		while(true){
			
			//paint the field
			field.paint(field.getGraphics());
			
			//move the tank
			tank.move(field.getGraphics(), Movable.Direction.RIGHT, step);
			
			synchronized (this) {
				
				try {
					this.wait(delay);
				} catch (InterruptedException e) {
					;
				}
				
			}
			
			
		}
		
	}

	
	
}
