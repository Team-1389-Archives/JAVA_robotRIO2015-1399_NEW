package org.usfirst.frc.team1389.robot;

import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PosTrack extends Component{

	Gyro gyro = Robot.state.gyro;
	AnalogAccelerometer accelX = Robot.state.accelX;
	AnalogAccelerometer accelY = Robot.state.accelY;
	Timer time = Robot.state.time;
	float dt = 0;
	float t1 = 0;
	float velX, velY, posX, posY;
	
	@Override
	public void teleopTick() {
		dt = (float) (time.get() - t1);
		t1 = (float) time.get();
		
		velX += (accelX.getAcceleration() * 9.8 * Math.cos(Math.toRadians(gyro.getAngle()) + accelY.getAcceleration() * 9.8 * Math.cos(Math.toRadians(gyro.getAngle())))) * dt;
		velY += (accelX.getAcceleration() * 9.8 * Math.sin(Math.toRadians(gyro.getAngle()) + accelY.getAcceleration() * 9.8 * Math.sin(Math.toRadians(gyro.getAngle())))) * dt;
		
		posX += velX * dt;
		posY += velY * dt;
		
		SmartDashboard.putNumber("X Displacment", posX);
		SmartDashboard.putNumber("Y Displacment", posY);
	}

	
	@Override
	public void autonTick() {
		teleopTick();
	}


}
