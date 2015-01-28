package org.usfirst.frc.team1389.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TeleopControl extends Control{
	final boolean invertedX = true;
	final boolean invertedY = false;
	private Component[] components;
	public TeleopControl(Component... components){
		this.components=components;
	}
	public void tick(){
		displayInfared(Robot.state.infared);
		DriveComponent driver=(DriveComponent)components[Component.DRIVE];
		double x = Robot.state.drive.getLeftX()*(invertedX?1:-1);
		double y = Robot.state.drive.getLeftY()*(invertedY?1:-1);
		driver.drive(x,y);
	}
	private void displayInfared(DigitalInput[] sensors) {
		for(int x=0;x<sensors.length; x++){
			SmartDashboard.putBoolean("IR "+x, sensors[x].get());
		}
	}
	
}
