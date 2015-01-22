package org.usfirst.frc.team1389.robot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class ElevatorControl extends Component{

	
	//Our IR sensors output low when an object is within 2cm - 10cm of an object (e. g. !IRa.get() equates to true when there is an object in front of sensor one) 
	//Sense stores the boolean returns of each IR sensor, updating each tick. Last sense is the same as sense,
	//but values only update when a IR sensor is passed
	int direction;
	DigitalSwitch[] stateSave;

	public void teleopConfig(){}
	
	public void teleopTick(InputState state)
	{
		SmartDashboard.putBoolean("IR One value", state.getInfared()[0].isOn());
		stateSave=state.getInfared().clone();
		elevator.set(direction() * Constants.ELEVATOR_SPEED_MOD);
	}


/** 
 * When input is given to bring the elevator to a specified level, this function provides the direction necessary to do so.
 * @param senseID represents the desired level you want to go to (Integers 0 - 4)
 * @param direction represents the direction of the input last pressed so we are able to know where to send the elevator in the case that the desired level
	is the same as the last IR recognized
 * @return -1 represents down, 
 */
	public int getDirection(int senseID, InputState state)
	{
		
		 
		
		for (int i = 0; i < state.getInfared().length; i++)
		{
			if (stateSave[i])
			{
				if (i < senseID)
					return 1;
				if (i > senseID)
					return -1;
				if (i == senseID)
					return direction * -1;
			}
		}
		return 0;

	}
	public int direction(InputState state){
		if (!IRa.get() || !IRb.get() || !IRc.get() || !IRd.get() || !IRe.get())
		{
			lastSense[0] = !IRa.get(); lastSense[1] = !IRb.get(); lastSense[2] = !IRc.get(); lastSense[3] = !IRd.get(); lastSense[4] = !IRe.get();
		}
		
		if (state.getManip().getLeftY() > .4 && !sense[4])
		{
			direction = 1;
		}
		if (state.getManip().getLeftY() < -.4 && !sense[0])
		{
			direction = -1;
		}
		if (state.getManip().isButtonA() && !sense[1])
		{
			direction = getDirection(1);
		}
		if (state.getManip().isButtonX() && !sense[2])
		{
			direction = getDirection(2);
		}
		if (state.getManip().isButtonB() && !sense[3])
		{
			direction = getDirection(3);
		}
	}

	public void autonConfig(){}
	public void autonTick(){}

	public void test() {}

}
