package org.usfirst.frc.team1389.robot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class ElevatorControl extends Component{
	
	public ElevatorControl(){
		componentType="Elevator";
	}
	
	SpeedController elevator= new Victor(Constants.ELEVATOR_PWM);
	//Our IR sensors output low when an object is within 2cm - 10cm of an object (e. g. !IRa.get() equates to true when there is an object in front of sensor one) 
	//Sense stores the boolean returns of each IR sensor, updating each tick. Last sense is the same as sense,
	//but values only update when a IR sensor is passed
	
	public void teleopConfig(){
	}
	public void teleopTick(InputState state){
		DigitalInput[] sensors=state.getInfared();
		int lastSensor=0;
		for(int d=0;d<sensors.length;d++){
			if(!sensors[d].get())lastSensor=d;
		}
		SmartDashboard.putBoolean("IR One value", state.getInfared()[0].get());
		elevator.set(direction(state, sensors, lastSensor) * Constants.ELEVATOR_SPEED_MOD);
	}


/** 
 * When input is given to bring the elevator to a specified level, this function provides the direction necessary to do so.
 * @param senseID represents the desired level you want to go to (Integers 0 - 4)
 * @param direction represents the direction of the input last pressed so we are able to know where to send the elevator in the case that the desired level
	is the same as the last IR recognized
 * @return -1 represents down, 
 */
	public int whereToGo(int senseID, int lastSensor)
	{
		if (lastSensor > senseID){ //above the requested spot
			return 1;
		}
		else if (lastSensor < senseID){ //below the requested spot
			return -1;
		}
		else{
			return 0;
		}

	}
	public int direction(InputState state,DigitalInput[] sensors, int lastSensor){
		
		if (state.getManip().getLeftY() > .4 && !sensors[4].get())
		{
			return 1;
		}
		else if (state.getManip().getLeftY() < -.4 && !sensors[0].get())
		{
			return -1;
		}
		else if (state.getManip().isButtonA() && !sensors[1].get())
		{
			return whereToGo(1, lastSensor);
		}
		else if (state.getManip().isButtonX() && !sensors[2].get())
		{
			return whereToGo(2, lastSensor);
		}
		else if (state.getManip().isButtonB() && !sensors[3].get())
		{
			return whereToGo(3, lastSensor);
		}
		return 0;
	}

	public void autonConfig(){}
	public void autonTick(){}
	public void test() {}

}
