package org.usfirst.frc.team1389.robot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class ElevatorComponent extends Component{
	
	public ElevatorComponent(){
		componentType="Elevator";
	}
	
	SpeedController elevator= new Victor(Constants.ELEVATOR_PWM);
	//Our IR sensors output low when an object is within 2cm - 10cm of an object (e. g. !IRa.get() equates to true when there is an object in front of sensor one) 
	//Sense stores the boolean returns of each IR sensor, updating each tick. Last sense is the same as sense,
	//but values only update when a IR sensor is passed
	
	public void goTo(int loc,DigitalInput[] sensors){
		int lastSensor=0;
		for(int d=0;d<sensors.length;d++){
			if(!sensors[d].get())lastSensor=d;
		}
		elevator.set(whereToGo(loc, lastSensor) * Constants.ELEVATOR_SPEED_MOD);
	}
	
	public void move(int direction, DigitalInput[] sensors){
		int dir=0;
		if (direction==1&&!sensors[4].get())
		{
			dir=1;
		}
		else if (direction==-1 && !sensors[0].get())
		{
			dir=-1;
		}
		elevator.set(dir*Constants.ELEVATOR_SPEED_MOD);
	}

/** 
 * When input is given to bring the elevator to a specified level, this function provides the direction necessary to do so.
 * @param senseID represents the desired elevator level (Integers 0 - 4)
 * @param saves last triggered infared sensor(last known location of elevator)
 * @return +/- direction to reach desired level
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

}
