package no.fd.robotics.lejos.brain;

import lejos.robotics.Color;
import no.fd.robotics.lejos.motor.MotorThread;
import no.fd.robotics.lejos.motor.MotorThread.MotorRequest;
import no.fd.robotics.lejos.sensor.ColorSensorThread;

public class FindYellowBrainThread implements Runnable {

	private MotorThread motorThread = null;
	private ColorSensorThread colorSensorThread;
	private boolean isRunning = true;
	private boolean isFinished = false;
	
	
	public FindYellowBrainThread(MotorThread motorThread, ColorSensorThread colorSensorThread) {
		this.motorThread = motorThread;
		this.colorSensorThread = colorSensorThread;
	}
	
	@Override
	public void run() {
		motorThread.addRequest(MotorRequest.FORWARD_SLOW);
		while (isRunning) {			
			int currentColor = colorSensorThread.getCurrentColor();
			if (currentColor == Color.YELLOW) {
				motorThread.addRequest(MotorRequest.STOP);
				isFinished = true;
			}			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isFinished() {
		return isFinished;
	}

	
	
}
