package org.usfirst.frc.team1389.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.visa.VisaException;

//import com.kauailabs.nav6.frc.BufferingSerialPort;
//import com.kauailabs.nav6.frc.IMU;
//import com.kauailabs.nav6.frc.IMUAdvanced;

public class InputState implements Cloneable{
	public XBoxController drive;
	public XBoxController manip;
	
	public Encoder encoder1;
	public Encoder encoder2;
	
	
	//public BufferingSerialPort serial_port;
    //public IMUAdvanced imu;
    

	public Gyro gyro;
	

	public Timer time;
	
	public ArrayList<DigitalInput> infared;
	public DigitalInput contactSensor;
	
	public InputState(){
		
		/*//Init IMU
		try {
			serial_port = new BufferingSerialPort(57600);
		} catch (VisaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 imu = new IMUAdvanced(serial_port);
		 
*/
		
		
		time = new Timer();
		time.start();
		
		drive = new XBoxController(Constants.DRIVE_JOY);
		manip = new XBoxController(Constants.MANIP_JOY);
		
		encoder1 = new Encoder(Constants.ENCODER_1A,Constants.ENCODER_1B);
		encoder2 = new Encoder(Constants.ENCODER_2A,Constants.ENCODER_2B);
		
		infared = new ArrayList<DigitalInput>();
		infared.add(new DigitalInput(Constants.INFRARED_ONE));
		infared.add(new DigitalInput(Constants.INFRARED_TWO));
		infared.add(new DigitalInput(Constants.INFRARED_THREE));
		infared.add(new DigitalInput(Constants.INFRARED_FOUR));
	}
	
	public void tick() {
		drive.tick();
		manip.tick();
	}

	 

}
