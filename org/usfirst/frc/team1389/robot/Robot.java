package org.usfirst.frc.team1389.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * robotRIO code 2015 for FRC team 1389 now in Java!
 * This year we are attempting to organize the code through the object oriented capabilities java has. The code is organized as follows:
 * All constants (i.e port numbers and joystick input values) are final members of the Constants class.
 * All static Object used (Motor controllers, sensors, joysticks, etc.) are declared/initialized by the Motors_Sensors object
 * All individual robot components (i.e. drive train, lift system, etc.) are separate objects.
 * For both the autonomous and teleoperated phases of the match, the each component as a config method and tick method.
 * Config is called at the beginning of the phase, which tick is called every tick during the phase. 
 * @author Paul LoBuglio
 */


//Component @ in0 = DriverControl
//Component @ in1 = ElevatorControl
//Component @ in2 = PosTrack
public class Robot extends SampleRobot {
	
	//instance variables
	static ArrayList<Component> components;
	static InputState state;
	final static int DRIVE=2,ELEVATOR=0,POS=1;
	
	
	/**
	 * Instantiates all static motors and sensors. 
	 * Instantiates all component objects
	 */
	public Robot()
	{
		state= new InputState();
		components = new ArrayList<Component>();
		components.add(new ElevatorControl());
		components.add(new PosTrack());
		components.add(new DriveControl((PosTrack)(components.get(POS))));
		
	}
	
	
	/**
	 * Teleoperated configuration
	 * Update each component each iteration through the ".teleopTick()" method
	 */
	public void operatorControl()
	{
		for (Component c: components){
			
			c.teleopConfig();
		}
		while (isOperatorControl())
		{
			state.tick();
			
			for (Component c: components){
				c.teleopTick();
			}
		}
		
	}

	@Override
	public void autonomous(){
		Autonomous auton=new Autonomous(1, components);
	}

	/**bot into auton
	 * go forward into autonomous zone
	 */
	
	/**
	 * Autonomous configuration
	 * Update each component through the ".autonTick()" method
	 */
	public static Component getComponent(int index){
		return components.get(index);
	}
}
