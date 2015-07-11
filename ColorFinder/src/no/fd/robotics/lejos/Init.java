package no.fd.robotics.lejos;

import java.io.IOException;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import no.fd.robotics.lejos.brain.FindYellowBrainThread;
import no.fd.robotics.lejos.motor.MotorThread;
import no.fd.robotics.lejos.sensor.ColorSensorThread;

public class Init {
	
	public static void main(String[] args) throws IOException, InterruptedException {		
		MotorThread mThread = new MotorThread(MotorPort.B, MotorPort.C);
		ColorSensorThread colorSensorThread = new ColorSensorThread(SensorPort.S3);
		FindYellowBrainThread brainThread = new FindYellowBrainThread(mThread, colorSensorThread);
		Thread motorWorker = new Thread(mThread);
		Thread colorSensorWorker = new Thread(colorSensorThread);
		Thread brainWorker = new Thread(brainThread);
		colorSensorWorker.start();
		motorWorker.start();
		brainWorker.start();
		while (!brainThread.isFinished()) {
			Thread.sleep(20);
		}
		colorSensorThread.stop();
		mThread.stop();		
	}

}
