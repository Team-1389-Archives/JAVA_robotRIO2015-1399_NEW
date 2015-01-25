package org.usfirst.frc.team1389.robot;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public abstract class Control implements Cloneable{
	protected XBoxController drive;
	protected XBoxController manip;
	
	protected Encoder encoder1;
	protected Encoder encoder2;
	
	protected Accelerometer accelerometer;
	
	protected DigitalInput limit1;
	protected DigitalInput limit2;
	
	
	protected DigitalInput[] infared;
	
	public Control(){
		accelerometer= new BuiltInAccelerometer();
		drive = new XBoxController(Constants.DRIVE_JOY);
		manip = new XBoxController(Constants.MANIP_JOY);
		
		encoder1 = new Encoder(Constants.ENCODER_1A,Constants.ENCODER_1B);
		encoder1 = new Encoder(Constants.ENCODER_2A,Constants.ENCODER_2B);
		
		limit1 = new DigitalInput(Constants.LIMIT_ONE);
		limit2 = new DigitalInput(Constants.LIMIT_TWO);
		
		infared = new DigitalInput[5];
		
		infared[0]= new DigitalInput(Constants.INFRARED_ONE);
		infared[1]= new DigitalInput(Constants.INFRARED_TWO);
		infared[2]= new DigitalInput(Constants.INFRARED_THREE);
		infared[3]= new DigitalInput(Constants.INFRARED_FOUR);
		infared[4]= new DigitalInput(Constants.INFRARED_FIVE);
	}
	public abstract void tick();

}
