package org.usfirst.frc.team1389.robot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class ElevatorControl extends Component{

	SpeedController elevator;
	//= new Victor(Constants.ELEVATOR_PWM);
	//Our IR sensors output low when an object is within 2cm - 10cm of an object (e. g. !IRa.get() equates to true when there is an object in front of sensor one) 
	//Sense stores the boolean returns of each IR sensor, updating each tick. Last sense is the same as sense,
	//but values only update when a IR sensor is passed
	int goToPosition = 0;
	boolean going;
	int direction;
	//PosTrack pos = (PosTrack)Robot.components.get(Robot.POS);
	DigitalInput IRa, IRb, IRc, IRd, IRe, IRf;
	boolean [] lastSense = new boolean [6];
	boolean []sense = new boolean [6];

	public ElevatorControl() {
		IRa = new DigitalInput (Constants.INFRARED_ONE);
		IRb = new DigitalInput (Constants.INFRARED_TWO);
		IRc = new DigitalInput (Constants.INFRARED_THREE);
		IRd = new DigitalInput (Constants.INFRARED_FOUR);
		IRe = new DigitalInput (Constants.INFRARED_FIVE);
		IRf = new DigitalInput (Constants.INFRARED_SIX);

	}



	public void autonTick(){
	}



	public void teleopConfig(){
		lastSense[0] = !IRa.get();  lastSense[0] = !IRb.get();  lastSense[0] = !IRc.get(); lastSense[0] = !IRd.get(); lastSense[0] = !IRe.get();  lastSense[0] = !IRf.get();
	}
	@Override
	public void teleopTick(){
		sense[0] = !IRa.get(); sense[1] = !IRb.get(); sense[2] = !IRc.get(); sense[3] = !IRd.get(); sense[4] = !IRe.get(); sense[5] = !IRf.get();
		if (!IRa.get() || !IRb.get() || !IRc.get() || !IRd.get() || !IRe.get())
		{
			lastSense[0] = !IRa.get(); lastSense[1] = !IRb.get(); lastSense[2] = !IRc.get(); lastSense[3] = !IRd.get(); lastSense[4] = !IRe.get(); lastSense[5] = !IRf.get();
		}


		if (Robot.state.manip.getLeftY() > .4 && !sense[5])
		{
			direction = 1;
			elevator.set(direction * Constants.ELEVATOR_SPEED_MOD);
		}
		if (Robot.state.manip.getLeftY() < -.4 && !sense[0])
		{
			direction = -1;
			elevator.set(direction * Constants.ELEVATOR_SPEED_MOD);
		}
		if (Robot.state.manip.isButtonA() && !sense[1])
		{
			direction = getDirection(1);
			elevator.set(direction * Constants.ELEVATOR_SPEED_MOD);
		}
		if (Robot.state.manip.isButtonX() && !sense[2])
		{
			direction = getDirection(2);
			elevator.set(direction * Constants.ELEVATOR_SPEED_MOD);
		}
		if (Robot.state.manip.isButtonB() && !sense[3])
		{
			direction = getDirection(3);
			elevator.set(direction * Constants.ELEVATOR_SPEED_MOD);
		}
		
		if (Robot.state.manip.isButtonY() && !sense[4])
		{
			direction = getDirection(4);
			elevator.set(direction * Constants.ELEVATOR_SPEED_MOD);
		}

	}


	//Our IR sensors output low when an object is within 2cm - 10cm of an object (e. g. !IRa.get() equates to true when there is an object in front of sensor one) 
	//Sense stores the boolean returns of each IR sensor; updating each tick. Last sense is the same as sense,
	//but values only update when a IR sensor is passed
	
	public int getDirection(int senseID)
	{
		for (int i = 0; i < lastSense.length; i++)
		{
			if (lastSense[i])
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

	public void move(double direction){

		going=false;
		DigitalInput[] sensors=Robot.state.infared;
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


}
