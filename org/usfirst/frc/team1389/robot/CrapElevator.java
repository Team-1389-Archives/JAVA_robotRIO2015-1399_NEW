package org.usfirst.frc.team1389.robot;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CrapElevator extends Component{
	Victor liftOne;
	Victor liftTwo;
	public CrapElevator(){
		liftOne=new Victor(Constants.ELEVATOR_ONE_PWM);
		liftTwo=new Victor(Constants.ELEVATOR_TWO_PWM);
		//lift = new Victor(Constants.ELEVATOR_PWM);
	}
	public void teleopConfig(){
		
	}
	public void teleopTick(){
		liftOne.set(Robot.state.manip.getRightY()*Constants.ELEVATOR_SPEED_MOD);
		liftTwo.set(Robot.state.manip.getRightY()*Constants.ELEVATOR_SPEED_MOD * -1);
		SmartDashboard.putNumber("righty", Robot.state.manip.getRightY());
	}
	
}
