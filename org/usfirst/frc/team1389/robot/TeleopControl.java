package org.usfirst.frc.team1389.robot;

public class TeleopControl extends Control{
	final boolean invertedX = true;
	final boolean invertedY = false;
	private Component[] components;
	public TeleopControl(Component... components){
		this.components=components;
	}
	public void tick(){
		DriveComponent driver=(DriveComponent)components[Component.DRIVE];
		double x = drive.getLeftX()*(invertedX?1:-1);
		double y = drive.getLeftY()*(invertedY?1:-1);
		driver.drive(x,y);
	}
}
