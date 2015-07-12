package no.fd.robotics.lejos;

import java.io.IOException;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import no.fd.robotics.lejos.brain.FindIRSource;
import no.fd.robotics.lejos.motor.MotorThread;
import no.fd.robotics.lejos.sensor.IRSensorThread;

public class Init {
	
	public static void main(String[] args) throws IOException, InterruptedException {		
		MotorThread mThread = new MotorThread(MotorPort.B, MotorPort.C);
		IRSensorThread irSensorThread = new IRSensorThread(SensorPort.S4);
		Delay.msDelay(5000);
		FindIRSource brainThread = new FindIRSource(mThread, irSensorThread);
		Thread motorWorker = new Thread(mThread);
		Thread brainWorker = new Thread(brainThread);
		motorWorker.start();
		brainWorker.start();
		while (!brainThread.isFinished()) {
			Thread.sleep(20);
		}
		irSensorThread.stop();
		mThread.stop();		
	}

}
