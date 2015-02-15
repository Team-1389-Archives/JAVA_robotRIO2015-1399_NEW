package org.usfirst.frc.team1389.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class JacobAriElevatorCoeficientColliderUnicornCramberryGazpacho extends Component{
	Victor liftOne;
	Victor liftTwo;
		
	int lastSeen;
	int position;
	boolean isTouching;
	
	int wanted;
	
	enum Movement{
		UP,
		DOWN,
		STOP
	}
	
	public JacobAriElevatorCoeficientColliderUnicornCramberryGazpacho(){
		liftOne=new Victor(Constants.ELEVATOR_ONE_PWM);
		liftTwo=new Victor(Constants.ELEVATOR_TWO_PWM);
	}
	public void teleopConfig(){
		lastSeen = 0;
		isTouching = false;
		wanted = 0;
	}
	public void teleopTick(){
		updateSensors();
		updateUserInput();
		switch(move()){
		case UP:
			goUp();
			SmartDashboard.putString("intended direction", "UP");
			break;
		case DOWN:
			goDown();
			SmartDashboard.putString("intended direction", "DOWN");
			break;
		case STOP:
			stop();
			SmartDashboard.putString("intended direction", "STOP");
			break;
		}
				
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
			wanted = Robot.state.infared.size();
		}
	}
	
	private Movement move(){//untested
		int lowerSensor = wanted - 1;
		int upperSensor = wanted;
		/*if (lastSeen < lowerSensor){
			return Movement.UP;
		} else if (lastSeen > upperSensor) {
			return Movement.DOWN;
		} else { //lastSeen == upperSensor || lastSeen == lowerSensor
			if (isTouching){
				if (lastSeen == lowerSensor){
					return Movement.UP;
				} else { //lastSeen == upperSensor
					return Movement.DOWN;
				}
			}else{
				return Movement.STOP;
			}
			
		}*/
		
		if (lastSeen < lowerSensor || lastSeen == lowerSensor && isTouching){
			return Movement.UP;
		} else if (lastSeen > upperSensor || lastSeen == upperSensor && isTouching){
			return Movement.DOWN;
		} else {
			return Movement.STOP;
		}
	}

	private void setMotors(double speed){
		liftOne.set(speed * Constants.ELEVATOR_SPEED_MOD * -1);
		liftTwo.set(speed * Constants.ELEVATOR_SPEED_MOD);
	}
	
	private void goUp(){
		setMotors(1);
	}
	private void goDown(){
		setMotors(-.3);
	}
	private void stop(){ 
		setMotors(.3);
	}
}
