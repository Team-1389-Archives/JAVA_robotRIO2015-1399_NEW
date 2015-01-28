package org.usfirst.frc.team1389.robot;

public class Component implements phaseMethods{
	
	protected String componentType;
	
	@Override
	public void test() {
	}

	@Override
	public void autonConfig() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void autonTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void teleopConfig() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void teleopTick(InputState state) {
		
	}
	public String toString(){
		return componentType+"Component";
	}
	
}
