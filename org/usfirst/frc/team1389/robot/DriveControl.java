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
	
	public DriveControl() {
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
			//Debug
			SmartDashboard.putNumber("Driver LeftX", x);
			SmartDashboard.putNumber("Driver LeftY", y);
			SmartDashboard.putNumber("Left Power", (y + x) / Constants.LIMITER);
			SmartDashboard.putNumber("Right Power", (y - x) / Constants.LIMITER);
		x += selfTurn(state);
		double leftVel=(y + x) / Constants.LIMITER;
		double rightVel=(y - x) / Constants.LIMITER * -1;
		//VerifyVelocity(leftVel,rightVel,state.getEncoder1(),state.getEncoder2());
		LFDrive.set(leftVel);
		LBDrive.set(leftVel);
		RFDrive.set(rightVel);
		RBDrive.set(rightVel);
	}

	private void VerifyVelocity(double leftVel, double rightVel,
		Encoder encoder1, Encoder encoder2) {
		if((leftVel/rightVel)-(encoder1.getRate()/encoder2.getRate())>.3){
		}
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
