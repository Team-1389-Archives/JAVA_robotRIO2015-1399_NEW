package org.usfirst.frc.team1389.robot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveControl extends Component{

	Talon RFDrive;
	Talon RBDrive;
	Talon LFDrive;
	Talon LBDrive;
	double leftCoef;
	double rightCoef;

	final boolean encoderVerified=true;
	final AssistedRampUp rampUpState=AssistedRampUp.COMPUTER_ASSISTED;

	double actualLeft = 0, actualRight = 0;

	public DriveControl() {
		leftCoef=1;
		rightCoef=1;
		RFDrive = new Talon(Constants.RF_PWM_DRIVE);
		RBDrive = new Talon(Constants.RB_PWM_DRIVE);
		LFDrive = new Talon(Constants.LF_PWM_DRIVE);
		LBDrive = new Talon(Constants.LB_PWM_DRIVE);
	}	

	public void drive(double x,double y, InputState state){

		double leftPower=(y + x) / Constants.LIMITER;
		double rightPower=(y - x) / Constants.LIMITER * -1;

		if(rampUpState==AssistedRampUp.COMPUTER_ASSISTED){
			actualLeft=AssistedPowerControl(leftPower,actualLeft);
			actualRight=AssistedPowerControl(rightPower,actualRight);
		}
		else if(rampUpState==AssistedRampUp.STRICT_COMPUTER){
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
			VerifyVelocity(leftPower, rightPower, state.getEncoder1(), state.getEncoder2());
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
	public void teleopTick(InputState state)
	{
		drive(state.getDrive().getLeftX(), state.getDrive().getLeftY(),state);//TODO make only state get passed
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
