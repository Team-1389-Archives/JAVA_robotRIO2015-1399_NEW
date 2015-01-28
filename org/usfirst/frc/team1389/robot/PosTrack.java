package org.usfirst.frc.team1389.robot;

import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Timer;

public class PosTrack extends Component {

	Gyro gyro;
	AnalogAccelerometer accel;
	Timer time;
	float dt;


	@Override
	public void teleopTick(InputState state) {
		super.teleopTick(state);
	}

}
