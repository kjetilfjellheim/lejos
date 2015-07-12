package no.fd.robotics.lejos.brain;

import no.fd.robotics.lejos.motor.MotorThread;
import no.fd.robotics.lejos.motor.MotorThread.MotorRequest;
import no.fd.robotics.lejos.sensor.IRSensorThread;

public class FindIRSource implements Runnable {

	private MotorThread motorThread = null;
	private IRSensorThread irSensorThread;
	private boolean isRunning = true;
	private boolean isFinished = false;
	
	
	public FindIRSource(MotorThread motorThread, IRSensorThread irSensorThread) {
		this.motorThread = motorThread;
		this.irSensorThread = irSensorThread;
	}
	
	@Override
	public void run() {		
		while (isRunning) {						
			if (irSensorThread.getRange() != Float.POSITIVE_INFINITY) {
				motorThread.addRequest(MotorRequest.FORWARD_SLOW);
				isFinished = true;
			} else {
				motorThread.addRequest(MotorRequest.ROTATE_SPOT_RIGHT);
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
