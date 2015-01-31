package org.usfirst.frc.team1389.robot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveControl extends Component{
	final int STRICT_COMPUTER=0;
	final int FULL_USER=1;
	final int COMPUTER_ASSISTED=2;
	Talon RFDrive;
	Talon RBDrive;
	Talon LFDrive;
	Talon LBDrive;
	double leftCoef;
	double rightCoef;
	int rampUpState;
	PosTrack pos;
	final boolean encoderVerified=true;

	double actualLeft = 0, actualRight = 0;
	
	public DriveControl(PosTrack pos) {
		this.pos=pos;
		rampUpState=COMPUTER_ASSISTED;
		leftCoef=1;
		rightCoef=1;
		RFDrive = new Talon(Constants.RF_PWM_DRIVE);
		RBDrive = new Talon(Constants.RB_PWM_DRIVE);
		LFDrive = new Talon(Constants.LF_PWM_DRIVE);
		LBDrive = new Talon(Constants.LB_PWM_DRIVE);
	}	

	public void drive(double x,double y){
		
		double leftPower=(y + x) / Constants.LIMITER;
		double rightPower=(y - x) / Constants.LIMITER * -1;

		if(rampUpState==COMPUTER_ASSISTED){
			actualLeft=AssistedPowerControl(leftPower,actualLeft);
			actualRight=AssistedPowerControl(rightPower,actualRight);
		}
		else if(rampUpState==STRICT_COMPUTER){
			actualLeft=PowerControl(leftPower,actualLeft);
			actualRight=PowerControl(rightPower,actualRight);
		}
		else{
			actualLeft=leftPower;
			actualRight=rightPower;
		}

		LFDrive.set(actualLeft);
		LBDrive.set(actualLeft);
		RFDrive.set(actualRight);
		RBDrive.set(actualRight);

		if(encoderVerified){
			VerifyVelocity(leftPower, rightPower, Robot.state.encoder1, Robot.state.encoder2);
			leftPower*=leftCoef;
			rightPower*=rightCoef;
		}

		SmartDashboard.putNumber("Power", (double)((int)(100*((actualLeft + actualRight) / 2)))/100);
	}

	/**
	 * used in computer assisted ramp up state, uses a proportional power increase/decrease curve to allow user override
	 * @param Power side-specific input from controller
	 * @param actualPower current power on the same side
	 * @return new actual power based on requested Power
	 */
	private double AssistedPowerControl(double Power, double actualPower){
		double proportionalChange = Constants.PERCENT_POWER_CHANGE * Math.abs(Power - actualPower);
		if (Power > actualPower + proportionalChange){
			actualPower += proportionalChange;
		} else if (Power < actualPower - proportionalChange){
			actualPower-= proportionalChange;
		} else {
			actualPower = Power;
		}
		return actualPower;
	}

	/**
	 * used in computer restricted ramp up state, power increase/decrease per tick is limited to a fixed value
	 * @param Power side-specific input from controller
	 * @param actualPower current power on the same side
	 * @return new actual power based on requested Power
	 */
	private double PowerControl(double Power, double actualPower){
		//TODO
		double proportionalChange = Constants.MAX_ACCELERATION;
		if (Power > actualPower + proportionalChange){
			actualPower += proportionalChange;
		} else if (Power < actualPower - proportionalChange){
			actualPower-= proportionalChange;
		} else {
			actualPower = Power;
		}
		return actualPower;
	}
	private void VerifyVelocity(double leftVel, double rightVel,
			Encoder encoder1, Encoder encoder2) {
		final double multiplier=1;
		double error=(leftVel/rightVel)-(encoder1.getRate()/encoder2.getRate());
		if(error>0){//turning desired direction too fast
			leftCoef*=Math.abs(error)*multiplier;
			rightCoef/=Math.abs(error)*multiplier;
		}
		else if(error<0){//turning desired direction too slow
			leftCoef/=Math.abs(error)*multiplier;
			rightCoef*=Math.abs(error)*multiplier;
		}
	}

	@Override
	public void teleopConfig(){}

	/**
	 * Teleoperated control for the drive train
	 */
	@Override
	public void teleopTick()
	{
		String rampUp = null;
		switch(rampUpState){
			case STRICT_COMPUTER:rampUp="Strict";
			break;
			case FULL_USER:rampUp="User";
			break;
			case COMPUTER_ASSISTED:rampUp="Assisted";
			break;
			default:rampUp="null";
		}
		SmartDashboard.putString("RampUp", rampUp);
		SmartDashboard.putNumber("state", rampUpState);
		if(Robot.state.drive.isBPressed()){
			rampUpState++;
			rampUpState%=3;
		}
		drive(Robot.state.drive.getLeftX(), Robot.state.drive.getLeftY() * -1); 
	}
	/**
	 * autonomous drive system
	 * @param distance the distance to drive
	 * @param speed min:0 max:1
	 * simulates xbox control stick
	 */
	private double forward(double distance, double speed){
		if(pos.distance>=distance)return distance;
		else{
			drive(speed,0);
			return forward(distance,speed);
		}
	}
	
	/**
	 * Drive train Autonomous setup
	 */
	@Override
	public void autonConfig(){}

	/**
	 * Instructions for drive train at each autonomous tick. 
	 */
	@Override
	public void autonTick(){}
	@Override	
	public void test(){

	}
}
