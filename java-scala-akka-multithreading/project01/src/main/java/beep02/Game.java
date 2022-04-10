package beep02;

import java.awt.Color;

import javax.swing.JFrame;



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
 * The start point
 *
 */
public class Game {
	
	//set some initial parameters
	private static int fieldWidth = 680;
	private static int fieldHeight = 420;
	private static int tankRadius = 20;
	private static long tankMoveDelay = 150;
	private static int  tankMoveStep = 2;
	
	
	public static void main(String[] args) {
		
		
		//create the Window
		JFrame frame = new JFrame("The Java Scala Akka concurency, project 01");
		
		//create the canvas
		BattleField field = new BattleField();
		frame.setSize(fieldWidth, fieldHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(field);
        frame.setVisible(true);
        field.setBackground(Color.WHITE);
        
        
        //create the tank
        Tank tank = new Tank(tankRadius, Color.WHITE, Color.RED,  fieldWidth/2 + tankRadius/2, fieldHeight/2 - tankRadius/2);
        
        //create the engine (thread)
        Engine e  = new Engine(field, tank, tankMoveDelay, tankMoveStep);
        
        //start the engine
        e.start();
        
        //continue to run the main thread about 5 seconds
        int mainThreadCounter =  0;
        while(mainThreadCounter < 100){
        	synchronized (Game.class) {
        		try {
					Game.class.wait(50L);
				} catch (InterruptedException e1) {
					;
				}
			}
        	mainThreadCounter++;
        }
        System.out.println("The game ends now, because the main thread worked long enough");
        
        //Game over!
        System.exit(0);
        
	}
	
	
}
