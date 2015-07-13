package no.fd.robotics.lejos;


import java.io.IOException;

import lejos.hardware.port.SensorPort;
import no.fd.robotics.lejos.brain.DetectBreak;
import no.fd.robotics.lejos.sensor.IRSensorThread;

public class Init {
	
	public static void main(String[] args) throws IOException, InterruptedException {	
		IRSensorThread irSensorThread = new IRSensorThread(SensorPort.S4, 1);
		DetectBreak brainThread = new DetectBreak(irSensorThread);
		Thread brainWorker = new Thread(brainThread);
		Thread irWorker = new Thread(irSensorThread);		
		irWorker.start();
		brainWorker.start();		
		while (true) {
			Thread.sleep(500);
		}
	}

}
