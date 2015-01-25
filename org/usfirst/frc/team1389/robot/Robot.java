package org.usfirst.frc.team1389.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
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

public class Robot extends IterativeRobot {
	//instance variables
	Component[] components;
	Control state;
	
	/**Instantiates all component objects and input state
	 */
	public Robot(){
		components = new Component[2];
		components[Component.DRIVE]=new DriveComponent();
		components[Component.ELEVATOR]=new ElevatorComponent();
	}
	
	
	/**Teleoperated configuration
	 * called every time teleop is initiated
	 */
	@Override
	public void teleopInit(){
		state=new TeleopControl(components);
	}
	
	
	/**Teleoperated periodic method
	 * Called about 50 times per second
 	 */
	@Override
	public void teleopPeriodic() //Called about 50 times per second
	{
		state.tick();
	}
}
