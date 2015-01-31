package org.usfirst.frc.team1389.robot;

import java.util.ArrayList;

public class Autonomous {
	
	//These constants hold relevant distances we need to travel in inches
	private final double MULTIPLIER=0.0254*16;//inches->meters conversion * 1/16 scale model conversion
	private final double STAG_TO_AUTO = 80; //Distance from in front of AutoTotes -> AutoZone.
	private final double BETW_AUTO_TOTES = 85; //Distance to travel inbetween auto totes when picking up all totes
	
	private DriveControl drive;
	private ElevatorControl elevator;
	private PosTrack pos;
	
	public Autonomous(int methodNum,ArrayList<Component> components)
	{
		pos=(PosTrack)components.get(Robot.POS);
		drive=(DriveControl)components.get(Robot.DRIVE);
		elevator=(ElevatorControl)components.get(Robot.ELEVATOR);
		switch(methodNum)
		{
		case 1: autonOne(); break;
		case 2: autonTwo(); break;
		case 3: autonThree(); break;
		case 4: autonFour(); break;
		case 5: autonFive(); break;
		case 6: autonSix(); break;
		case 7: autonSeven(); break;
		default: break;
		}
	}

	public void autonOne()
	{
		
	}

	public void autonTwo()
	{
		
	}

	public void autonThree()
	{

	}

	public void autonFour()
	{

	}

	public void autonFive()
	{

	}

	public void autonSix()
	{

	}

	public void autonSeven()
	{

	}


}
