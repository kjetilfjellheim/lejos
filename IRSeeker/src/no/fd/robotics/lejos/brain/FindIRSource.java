package no.fd.robotics.lejos.brain;

import no.fd.robotics.lejos.motor.MotorThread;
import no.fd.robotics.lejos.motor.MotorThread.MotorRequest;
import no.fd.robotics.lejos.sensor.IRSensorThread;

public class FindIRSource implements Runnable {

	private IRSensorThread irSensorThread;
	private MotorThread motorThread;
	private boolean isRunning = true;

	float range = 100f;
	float newDirection = 0.0f;

	public FindIRSource(IRSensorThread irSensorThread, MotorThread motorThread) {
		this.irSensorThread = irSensorThread;
		this.motorThread = motorThread;
	}

	@Override
	public void run() {
		while (isRunning) {
			float[] checkRanges = irSensorThread.getValue();
			if (checkRanges != null) {
				if (checkRanges[1] != Float.POSITIVE_INFINITY) {								
					newDirection = checkRanges[0];
					range = checkRanges[1];
				}
				if (range > 5f) {
					if (newDirection < -3.0f) {
						if (motorThread.getCurrentRequest() != MotorRequest.FORWARD_ROTATE_LEFT) {
							System.out.println("Newdir: " + newDirection);
							System.out.println("Engine request: " + MotorRequest.FORWARD_ROTATE_LEFT);
							motorThread.addRequest(MotorRequest.FORWARD_ROTATE_LEFT);
						}
					} else if (newDirection > 3.0f) {
						if (motorThread.getCurrentRequest() != MotorRequest.FORWARD_ROTATE_RIGHT) {
							System.out.println("Newdir: " + newDirection);
							System.out.println("Engine request: " + MotorRequest.FORWARD_ROTATE_RIGHT);
							motorThread.addRequest(MotorRequest.FORWARD_ROTATE_RIGHT);
						}
					} else {
						if (motorThread.getCurrentRequest() != MotorRequest.FORWARD_MEDIUM) {
							System.out.println("Newdir: " + newDirection);
							System.out.println("Engine request: " + MotorRequest.FORWARD_MEDIUM);
							motorThread.addRequest(MotorRequest.FORWARD_MEDIUM);
						}
					}
				} else {
					motorThread.addRequest(MotorRequest.STOP);					
				}
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
