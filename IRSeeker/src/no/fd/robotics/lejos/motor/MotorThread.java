package no.fd.robotics.lejos.motor;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.robotics.RegulatedMotor;

public class MotorThread implements Runnable {
	private RegulatedMotor m1;
	private RegulatedMotor m2;

	private volatile MotorRequest request = MotorRequest.STOP;

	private boolean isRunning = true;

	private static final int ROTATE_SLOW = 200;
	private static final int ROTATE_MEDIUM = 500;
	private static final int ROTATE_FAST = 800;

	private volatile MotorRequest currentRequest = MotorRequest.STOP;

	public static enum MotorRequest {
		FORWARD_FAST, FORWARD_MEDIUM, FORWARD_SLOW, FORWARD_ROTATE_RIGHT, FORWARD_ROTATE_LEFT, STOP, ROTATE_SPOT_LEFT, ROTATE_SPOT_RIGHT
	}

	public MotorThread(Port engine1, Port engine2) {
		m1 = new EV3LargeRegulatedMotor(engine1);
		m2 = new EV3LargeRegulatedMotor(engine2);
	}

	@Override
	public void run() {
		while (isRunning) {
			if (request != currentRequest) {
				if (request == MotorRequest.FORWARD_SLOW) {
					m1.setSpeed(ROTATE_SLOW);
					m2.setSpeed(ROTATE_SLOW);
					m1.forward();
					m2.forward();
				} else if (request == MotorRequest.FORWARD_MEDIUM) {
					m1.setSpeed(ROTATE_MEDIUM);
					m2.setSpeed(ROTATE_MEDIUM);
					m1.forward();
					m2.forward();
				} else if (request == MotorRequest.FORWARD_FAST) {
					m1.setSpeed(ROTATE_FAST);
					m2.setSpeed(ROTATE_FAST);
					m1.forward();
					m2.forward();
				} else if (request == MotorRequest.FORWARD_ROTATE_RIGHT) {
					m2.setSpeed(ROTATE_SLOW);
					m2.forward();
					m1.flt(true);
				} else if (request == MotorRequest.FORWARD_ROTATE_LEFT) {
					m1.setSpeed(ROTATE_SLOW);
					m2.flt(true);
					m1.forward();
				} else if (request == MotorRequest.STOP) {
					m1.stop(true);
					m2.stop();
				} else if (request == MotorRequest.ROTATE_SPOT_RIGHT) {
					m1.setSpeed(ROTATE_SLOW);
					m2.setSpeed(ROTATE_SLOW);
					m1.backward();
					m2.forward();
				} else if (request == MotorRequest.ROTATE_SPOT_LEFT) {
					m1.setSpeed(ROTATE_SLOW);
					m2.setSpeed(ROTATE_SLOW);
					m1.forward();
					m2.backward();
				}
				System.out.println("Old: " + currentRequest);
				currentRequest = request;
				System.out.println("New: " + currentRequest);
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void addRequest(MotorRequest request) {
		this.request = request;
	}

	public void stop() {
		isRunning = false;
		m1.close();
		m2.close();
	}

	public boolean isStopped() {
		return currentRequest == MotorRequest.STOP;
	}

	public MotorRequest getCurrentRequest() {
		return currentRequest;
	}

}
