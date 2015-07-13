package no.fd.robotics.lejos;


import java.io.IOException;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import no.fd.robotics.lejos.brain.IRMapper;
import no.fd.robotics.lejos.motor.MotorThread;
import no.fd.robotics.lejos.sensor.IRSensorThread;

public class Init {
	
	public static void main(String[] args) throws IOException, InterruptedException {	
		IRSensorThread irSensorThread = new IRSensorThread(SensorPort.S4, 0);
		MotorThread motorThread = new MotorThread(MotorPort.B, MotorPort.C);
		IRMapper brainThread = new IRMapper(motorThread, irSensorThread);
		Thread brainWorker = new Thread(brainThread);
		Thread motorWorker = new Thread(motorThread);
		Thread irWorker = new Thread(irSensorThread);		
		irWorker.start();
		motorWorker.start();
		brainWorker.start();		
		while (true) {
			Thread.sleep(500);
		}
	}

}
