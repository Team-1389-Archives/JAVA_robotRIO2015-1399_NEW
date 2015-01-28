package org.usfirst.frc.team1389.robot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveControl extends Component{

	boolean invertedX = true;
	boolean invertedY = false;
	Talon RFDrive;
	Talon RBDrive;
	Talon LFDrive;
	Talon LBDrive;
	int rightCoef;
	int leftCoef;

	double actualLeft = 0, actualRight = 0;

	public DriveControl() {
		rightCoef=1;
		leftCoef=1;
		componentType="Drive";
		RFDrive = new Talon(Constants.RF_PWM_DRIVE);
		RBDrive = new Talon(Constants.RB_PWM_DRIVE);
		LFDrive = new Talon(Constants.LF_PWM_DRIVE);
		LBDrive = new Talon(Constants.LB_PWM_DRIVE);
	}	

	/**
	 * Drive train Teleoperated setup
	 */
	@Override
	public void teleopConfig(){}

	/**
	 * Teleoperated control for the drive train
	 */

	@Override
	public void teleopTick(InputState state)
	{
		double x = state.getDrive().getLeftX()*(invertedX?1:-1);
		double y = state.getDrive().getLeftY()*(invertedY?1:-1);
		x += selfTurn(state);
		double leftPower=leftCoef*(y + x) / Constants.LIMITER;
		double rightPower=rightCoef*(y - x) / Constants.LIMITER * -1;

		actualLeft=setPower(leftPower,actualLeft);
		actualRight=setPower(rightPower,actualRight);
		LFDrive.set(actualLeft);
		LBDrive.set(actualLeft);
		RFDrive.set(actualRight);
		RBDrive.set(actualRight);
		SmartDashboard.putNumber("Power", (double)((int)(100*((actualLeft + actualRight) / 2)))/100);
		//VerifyVelocity(actualLeft,actualRight,state.getEncoder1(),state.getEncoder2());
	}
	private double setPower(double Power, double actualPower){
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

	private void VerifyVelocity(double leftVel, double rightVel,
			Encoder encoder1, Encoder encoder2) {
		leftCoef-=(leftVel/rightVel)-(encoder1.getRate()/encoder2.getRate());
		rightCoef+=(leftVel/rightVel)-(encoder1.getRate()/encoder2.getRate());
	}

	public double selfTurn(InputState state)
	{
		if (state.getLimit1().get() ^ state.getLimit2().get())
		{
			if (state.getLimit1().get())
				return -.5;
			if (state.getLimit2().get())
				return .5;
		}
		return 0;
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
