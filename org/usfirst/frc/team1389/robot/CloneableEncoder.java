package org.usfirst.frc.team1389.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

public class CloneableEncoder implements Cloneable {
	private Encoder input;

	CloneableEncoder(int portA,int portB){
		input = new Encoder(portA,portB);
	}

	public void tick(){
	}

	protected CloneableEncoder clone() throws CloneNotSupportedException {
		return (CloneableEncoder) super.clone();
	}
	public Encoder getEncoder(){
		return input;
	}

}