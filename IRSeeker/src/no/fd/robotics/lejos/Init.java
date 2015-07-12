package no.fd.robotics.lejos;


import java.io.IOException;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import no.fd.robotics.lejos.brain.FindIRSource;
import no.fd.robotics.lejos.motor.MotorThread;
import no.fd.robotics.lejos.sensor.IRSensorThread;

public class Init {
	
	public static void main(String[] args) throws IOException, InterruptedException {	
		IRSensorThread irSensorThread = new IRSensorThread(SensorPort.S4, 1);
		MotorThread motorThread = new MotorThread(MotorPort.B, MotorPort.C);
		FindIRSource brainThread = new FindIRSource(irSensorThread, motorThread);
		Thread brainWorker = new Thread(brainThread);
		Thread motorWorker = new Thread(motorThread);
		motorWorker.start();
		brainWorker.start();		
		while (true) {
			Thread.sleep(10);
		}
	}

}
