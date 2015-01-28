package org.usfirst.frc.team1389.robot;

public abstract class Component{
	
	protected String componentType;
	static final int DRIVE=0,ELEVATOR=1;

	public String toString(){
		return componentType+"Component";
	}
	
}
