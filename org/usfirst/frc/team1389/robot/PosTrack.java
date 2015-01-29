package org.usfirst.frc.team1389.robot;

import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PosTrack extends Component{
	
	Gyro gyro;
	Timer time;
	Accelerometer accel;
	double dt = 0;
	double t1 = 0;
	double velX, velY, posX, posY;
	
	public PosTrack(){
		accel=Robot.state.accel;
		gyro = Robot.state.gyro;
		time = Robot.state.time;
		velX=0;
		velY=0;
		posX=0;
		posY=0;
	}
	
	@Override
	public void teleopTick() {
		double accelX = Math.floor(100*accel.getX())/100;
		double accelY = Math.floor(100*accel.getY())/100;
		dt =(time.get() - t1);
		t1 = time.get();

		velX += (accelX* 9.8 * Math.cos(Math.toRadians(gyro.getAngle()) + accelY * 9.8 * Math.cos(Math.toRadians(gyro.getAngle())))) * dt;
		velY += (accelX* 9.8 * Math.sin(Math.toRadians(gyro.getAngle()) + accelY * 9.8 * Math.sin(Math.toRadians(gyro.getAngle())))) * dt;
		
		posX += velX * dt;
		posY += velY * dt;
		
		SmartDashboard.putNumber("velX", velX);
		SmartDashboard.putNumber("velY", velY);
		SmartDashboard.putNumber("X Displacment", posX);
		SmartDashboard.putNumber("Y Displacment", posY);
		SmartDashboard.putNumber("accelX", accelX);
		SmartDashboard.putNumber("accelY", accelY);
		SmartDashboard.putNumber("dt", dt);
		SmartDashboard.putNumber("t1", t1);
	}

	
	@Override
	public void autonTick() {
		teleopTick();
	}


}
