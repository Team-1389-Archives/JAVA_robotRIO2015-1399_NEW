package org.usfirst.frc.team1389.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

public class HolonomicDrive extends DriveControl{
	Victor centerMotor;
	public HolonomicDrive() {
		super();
		centerMotor = new Victor(Constants.CENTER_PWM);
		
	}
	
	
	@Override
	public void drive(double x, double y) {
		centerMotor.set(Robot.state.drive.getRightX());
		super.drive(x, y);
	}

}
