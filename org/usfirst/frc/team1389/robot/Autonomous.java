package org.usfirst.frc.team1389.robot;

import java.util.ArrayList;

public class Autonomous {
	
	private final double AUTON_SPEED_MOD = 1;

	//These constants hold relevant distances we need to travel in inches
	private final double MULTIPLIER=0.0254;//inches->meters conversion
	private final double TAPE_TO_LANDMARK = 107*MULTIPLIER; //Distance from in front of AutoTotes -> AutoZone.
	private final double STAGING_ZONE_WIDTH=48*MULTIPLIER;//length down the field of yellow crate zone
	private final double STAGING_ZONE_LENGTH=23*MULTIPLIER;//width of yellow crate zone
	private final double BETW_AUTO_TOTES = 33*MULTIPLIER; //Distance to travel in between auto totes when picking up all totes
	private final double TAPE_TO_DRIVER = 76*MULTIPLIER; //Distance to from down-field edge of staging zone to driver station
	private final double TOTE_WIDTH = 26.9*MULTIPLIER; //width of a tote
	private final double LANDFILL_TO_SCORING = 51.2*MULTIPLIER; //distance from two totes in landfill to white scoring platform
	private final double LANDFILL_TO_AUTON = 54.5*MULTIPLIER; //distance from white scoring platform to middle of auton zone
	private final double OVERHANG = 13.5*MULTIPLIER; //distance past chassis of lift arm
	private final double timePerLevel=1.5;
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
		drive.move(TAPE_TO_LANDMARK,AUTON_SPEED_MOD);
	}

	public void autonTwo()
	{
		double crateCarryDistance=(TAPE_TO_LANDMARK+STAGING_ZONE_LENGTH);
		drive.move(crateCarryDistance,AUTON_SPEED_MOD);
	}

	public void autonThree()
	{
		double distance1=TAPE_TO_DRIVER-13.5;
		double distance2=distance1-12;
		drive.move(distance1,AUTON_SPEED_MOD);
		elevator.goTo(4);
		while(!elevator.going);
		drive.move(12*MULTIPLIER,-AUTON_SPEED_MOD);
		drive.turn(-90);
		drive.move(TOTE_WIDTH/3, AUTON_SPEED_MOD);
		elevator.goTo(0);
		drive.turn(-90);
		drive.move(distance2, AUTON_SPEED_MOD);
		drive.move(TAPE_TO_LANDMARK, AUTON_SPEED_MOD);		
	}

	public void autonFour()
	{
		drive.move(LANDFILL_TO_SCORING, -AUTON_SPEED_MOD);
		elevator.goTo(2);
		wait(.5);
		drive.move(LANDFILL_TO_AUTON, -AUTON_SPEED_MOD);
	}

	public void autonFive()
	{

	}
	public void autonSix()
	{
		double angle=35;
		double distance1=.5*BETW_AUTO_TOTES*Math.cos(angle);
		double distance2=OVERHANG;
		for(int x=1;x<=2;x++){
		drive.move(OVERHANG, AUTON_SPEED_MOD);
		elevator.goTo(0);
		while(!elevator.going);
		elevator.goTo(1);
		wait(timePerLevel/3);
		drive.turn(angle);
		drive.move(distance1, AUTON_SPEED_MOD);
		drive.turn(angle-90);
		drive.move(distance1, AUTON_SPEED_MOD);
		drive.turn(-angle);
		}
		drive.move(OVERHANG, AUTON_SPEED_MOD);
		elevator.goTo(0);
		elevator.goTo(1);
		wait(timePerLevel/3);
		drive.turn(-90);
		drive.move(TAPE_TO_LANDMARK, AUTON_SPEED_MOD);

	}
	public void autonEight()
	{
		elevator.goTo(1);
		while(!elevator.going);
		drive.move(BETW_AUTO_TOTES, AUTON_SPEED_MOD);
		elevator.goTo(0);
		while(!elevator.going);
		elevator.goTo(1);
		while(!elevator.going);
		drive.move(BETW_AUTO_TOTES * MULTIPLIER, AUTON_SPEED_MOD);
		elevator.goTo(0);
		while(!elevator.going);
		elevator.goTo(1);
		while(!elevator.going);
		drive.turn(-90);
		drive.move(TAPE_TO_LANDMARK, AUTON_SPEED_MOD);

	}

	public void autonSeven()
	{
		double distance1=TAPE_TO_DRIVER-OVERHANG;
		double distance2=distance1-12+TAPE_TO_LANDMARK;
		drive.move(distance1,AUTON_SPEED_MOD);
		elevator.goTo(5);
		while(!elevator.going);
		drive.move(12*MULTIPLIER,-AUTON_SPEED_MOD);
		drive.turn(90);
		drive.move(BETW_AUTO_TOTES, AUTON_SPEED_MOD);
		drive.turn(90);
		drive.move(distance2, AUTON_SPEED_MOD);
	}
	public void wait(double time){
		//TODO
	}


}
