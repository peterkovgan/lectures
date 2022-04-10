package beep02;

import java.awt.Graphics;

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
 * The intention is to provide all objects with the facility to change the current place
 *
 */
public interface Movable {

	//movement directions
	enum Direction{
		RIGHT,
		LEFT,
		UP,
	    DOWN
	}
	
	/**
	 * 
	 * @param g - pass the graphics to the moving object
	 * @param direction
	 * @param steps
	 */
	public void move(Graphics g, Direction direction, int steps);
	
}
