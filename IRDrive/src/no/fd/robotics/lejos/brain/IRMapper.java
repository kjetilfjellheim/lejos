package no.fd.robotics.lejos.brain;

import java.util.ArrayList;
import java.util.List;

import no.fd.robotics.lejos.motor.MotorThread;
import no.fd.robotics.lejos.motor.MotorThread.MotorRequest;
import no.fd.robotics.lejos.sensor.IRSensorThread;

public class IRMapper implements Runnable {

	private MotorThread motorThread = null;
	private IRSensorThread irSensorThread;
	private boolean isRunning = true;
	private boolean isFinished = false;
	List<Float> distances = new ArrayList<Float>();

	// This variable contains number of changes between infinity and a number.
	public IRMapper(MotorThread motorThread, IRSensorThread irSensorThread) {
		this.motorThread = motorThread;
		this.irSensorThread = irSensorThread;
	}

	@Override
	public void run() {
		motorThread.addRequest(MotorRequest.ROTATE_SPOT_LEFT);
		
		while (isRunning) {
			float distance = irSensorThread.getValue();
			System.out.println(distance);
			distances.add(distance);
			
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
