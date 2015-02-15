package org.usfirst.frc.team1389.robot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class ElevatorControl extends Component{


	//= new Victor(Constants.ELEVATOR_PWM);
	//Our IR sensors output low when an object is within 2cm - 10cm of an object (e. g. !IRa.get() equates to true when there is an object in front of sensor one) 
	//Sense stores the boolean returns of each IR sensor, updating each tick. Last sense is the same as sense,
	//but values only update when a IR sensor is passed
	Victor elevatorOne, elevatorTwo;
	int goToPosition = 0;
	boolean going;
	int direction;
	//PosTrack pos = (PosTrack)Robot.components.get(Robot.POS);
	DigitalInput IRa, IRb, IRc, IRd, IRe;
	boolean [] lastSense = new boolean [5];
	boolean []sense = new boolean [5];

	public ElevatorControl() {
		elevatorOne = new Victor(Constants.ELEVATOR_ONE_PWM);
		elevatorTwo = new Victor(Constants.ELEVATOR_TWO_PWM);
		IRa = new DigitalInput (Constants.INFRARED_ONE);
		IRb = new DigitalInput (Constants.INFRARED_TWO);
		IRc = new DigitalInput (Constants.INFRARED_THREE);
		IRd = new DigitalInput (Constants.INFRARED_FOUR);
		//IRe = new DigitalInput (Constants.INFRARED_FIVE);
	}



	public void autonTick(){
	}



	public void teleopConfig(){
		lastSense[0] = !IRa.get();  lastSense[0] = !IRb.get();  lastSense[0] = !IRc.get(); lastSense[0] = !IRd.get(); lastSense[0] = !IRe.get();
	}
	@Override
	public void teleopTick(){
		sense[0] = !IRa.get(); sense[1] = !IRb.get(); sense[2] = !IRc.get(); sense[3] = !IRd.get(); sense[4] = !IRe.get();
		if (!IRa.get() || !IRb.get() || !IRc.get() || !IRd.get() || !IRe.get())
		{
			lastSense[0] = !IRa.get(); lastSense[1] = !IRb.get(); lastSense[2] = !IRc.get(); lastSense[3] = !IRd.get(); lastSense[4] = !IRe.get();
		}

		SmartDashboard.putBoolean("IR lvl 1", sense[0]);
		SmartDashboard.putBoolean("IR lvl 2", sense[1]);
		SmartDashboard.putBoolean("IR lvl 3", sense[2]);
		SmartDashboard.putBoolean("IR lvl 4", sense[3]);
		SmartDashboard.putBoolean("IR lvl 5", sense[4]);
		SmartDashboard.putBoolean("LastIR 1", lastSense[0]);
		SmartDashboard.putBoolean("LastIR 2", lastSense[1]);
		SmartDashboard.putBoolean("LastIR 3", lastSense[2]);
		SmartDashboard.putBoolean("LastIR 4", lastSense[3]);
		SmartDashboard.putBoolean("LastIR 5", lastSense[4]);

		SmartDashboard.putNumber("direction", direction);


		SmartDashboard.putNumber("leftY", Robot.state.manip.getLeftY());
		SmartDashboard.putBoolean("buttonA", Robot.state.manip.isButtonA());
		SmartDashboard.putBoolean("buttonX", Robot.state.manip.isButtonX());
		SmartDashboard.putBoolean("buttonB", Robot.state.manip.isButtonB());
		float y = (float) Robot.state.manip.getLeftY() * -1;
		if (Math.abs(y) < .2)
			direction = 0;

		if (!sense[4] && !sense[0])
		{
			if (y > .2)
			{
				direction = 1;
				elevatorOne.set(-y * Constants.ELEVATOR_SPEED_MOD);
				elevatorTwo.set(y * Constants.ELEVATOR_SPEED_MOD);
			}
			if (y < -.2)
			{
				direction = -1;
				elevatorOne.set(y * Constants.ELEVATOR_SPEED_MOD );
				elevatorTwo.set(-y * Constants.ELEVATOR_SPEED_MOD);
			}
		



		if (!Robot.state.manip.isButtonA() && !Robot.state.manip.isButtonB() && !Robot.state.manip.isButtonX())
			direction = 0;
		if (Robot.state.manip.isButtonA() && !sense[1])
		{
			direction = getDirection(1);
			elevatorOne.set(direction * Constants.ELEVATOR_SPEED_MOD * -1);
			elevatorTwo.set(direction * Constants.ELEVATOR_SPEED_MOD);
		}
		else if (Robot.state.manip.isButtonA() && sense[1])
			direction = 0;

		if (Robot.state.manip.isButtonX() && !sense[2])
		{
			direction = getDirection(2);
			elevatorOne.set(direction * Constants.ELEVATOR_SPEED_MOD * -1);
			elevatorTwo.set(direction * Constants.ELEVATOR_SPEED_MOD);
		}
		else if (Robot.state.manip.isButtonX() && sense[2])
			direction = 0;
		if (Robot.state.manip.isButtonB() && !sense[3])
		{
			direction = getDirection(3);
			elevatorOne.set(direction * Constants.ELEVATOR_SPEED_MOD * -1);
			elevatorTwo.set(direction * Constants.ELEVATOR_SPEED_MOD);
		}
		else if (Robot.state.manip.isButtonB() && sense[3])
			direction = 0;
		}
		if (sense[4])
			elevatorOne.set(Constants.ELEVATOR_SPEED_MOD * -1);
		if (sense[0])
			elevatorOne.set(Constants.ELEVATOR_SPEED_MOD);
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
		DigitalInput[] sensors = (DigitalInput[])Robot.state.infared.toArray();
		int dir=0;
		if (direction==1&&!sensors[4].get())
		{
			dir=1;
		}
		else if (direction==-1 && !sensors[0].get())
		{
			dir=-1;
		}
		elevatorOne.set(dir*Constants.ELEVATOR_SPEED_MOD * -1);
		elevatorTwo.set(dir*Constants.ELEVATOR_SPEED_MOD);
	}



	/** 
	 * When input is given to bring the elevator to a specified level, this function provides the direction necessary to do so.
	 * @param senseID represents the desired elevator level (Integers 0 - 4)
	 * @param saves last triggered infared sensor(last known location of elevator)
	 * @return +/- direction to reach desired level
	 */	


}
