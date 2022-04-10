package beep02;

import java.awt.Color;
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
 * The tank description
 *
 */
public class Tank implements Movable {
    
	private int radius;
	private Color colorToVanish; 
	private Color colorToAppear;
	
	private int currentPositionX;
	private int currentPositionY;
	
	/**
	 * 
	 * @param radius 
	 * @param colorToVanish    - this color is to paint the past location of the tank (tank vanishes)
	 * @param colorToAppear    - this color is to paint the current location of the tank (tank reappears)
	 * @param currentPositionX - a position of the tank on axis X
	 * @param currentPositionY - a position of the tank on axis Y
	 */
	public Tank(int radius, Color colorToVanish, Color colorToAppear, int currentPositionX, int currentPositionY) {
		this.radius = radius;
		this.colorToVanish = colorToVanish;
		this.colorToAppear = colorToAppear;
		this.currentPositionX = currentPositionX;
		this.currentPositionY = currentPositionY;
	}
	
	
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	
	public Color getColorToVanish() {
		return colorToVanish;
	}
	public void setColorToVanish(Color colorToVanish) {
		this.colorToVanish = colorToVanish;
	}
	public Color getColorToAppear() {
		return colorToAppear;
	}
	public void setColorToAppear(Color colorToAppear) {
		this.colorToAppear = colorToAppear;
	}
	public void move(Graphics g, Movable.Direction direction, int steps) {
		
		//clean up the past location
		g.setColor(colorToVanish);
		g.drawOval(currentPositionX, currentPositionY, radius, radius);
		g.fillOval(currentPositionX, currentPositionY, radius, radius);
		
		//change position
		switch(direction){
			case LEFT: 
				currentPositionX = currentPositionX-steps;
			   	break;
			case DOWN:
				currentPositionY = currentPositionY-steps;
				break;
			case RIGHT:
				currentPositionX = currentPositionX+steps;
				break;
			case UP:
				currentPositionY = currentPositionY+steps;
				break;
			default:
				break;
		}
		
		//repaint
		g.setColor(colorToAppear);
		g.drawOval(currentPositionX, currentPositionY, radius, radius);
		g.fillOval(currentPositionX, currentPositionY, radius, radius);
	}


	
	
}
