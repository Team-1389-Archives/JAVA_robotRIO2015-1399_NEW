package org.usfirst.frc.team1389.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class JacobElevator extends Component{
	Victor liftOne;
	Victor liftTwo;
	
	DigitalInput[] IRs;
	
	int lastSeen;
	boolean isTouching;
	
	int wanted;
	
	public JacobElevator(){
		liftOne=new Victor(Constants.ELEVATOR_ONE_PWM);
		liftTwo=new Victor(Constants.ELEVATOR_TWO_PWM);
	}
	public void teleopConfig(){
		lastSeen = 0;
		isTouching = false;
	}
	public void teleopTick(){
		updateSensors();
		updateUserInput();
		move();
		SmartDashboard.putNumber("last seen", lastSeen);
		SmartDashboard.putBoolean("isTouching", isTouching);
		SmartDashboard.putNumber("wanted", wanted);
	}
	
	private void updateSensors(){
		isTouching = false;
		for (int i = 0; i < Robot.state.infared.size(); ++i){
			if (!Robot.state.infared.get(i).get()){
				lastSeen = i;
				isTouching = true;
			}
		}
	}
	
	private void updateUserInput(){
		if (Robot.state.manip.isAPressed()){
			++wanted;
		} else if (Robot.state.manip.isBPressed()){
			--wanted;
		}
		
		if (wanted < 0){
			wanted = 0;
		}
		
		if (wanted >= Robot.state.infared.size()){
			wanted = Robot.state.infared.size() - 1;
		}
	}
	
	private void move(){//untested
		if (lastSeen < wanted){
			goUp();
		} else if (lastSeen > wanted) {
			goDown();
		} else { //lastSeen == wanted
			if (!isTouching){
				goUp();
			} else {
				stop();
			}
		}
	}
	
	private void setMotors(double speed){
		liftOne.set(speed * Constants.ELEVATOR_SPEED_MOD * -1);
		liftTwo.set(speed * Constants.ELEVATOR_SPEED_MOD);
	}
	
	private void goUp(){
		setMotors(.75);
	}
	private void goDown(){
		setMotors(-.3);
	}
	private void stop(){ 
		setMotors(.05);
	}
}
