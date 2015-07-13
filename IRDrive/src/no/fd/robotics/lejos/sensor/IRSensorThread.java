package no.fd.robotics.lejos.sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.RangeFinderAdapter;

public class IRSensorThread implements Runnable {

	private EV3IRSensor sensor;

	private RangeFinderAdapter finderAdapter;

	private volatile float irResponse;

	public IRSensorThread(Port port, int mode) {
		sensor = new EV3IRSensor(port);
		finderAdapter = new RangeFinderAdapter(sensor.getMode(mode));
	}

	public float getValue() {
		return irResponse;
	}

	public void stop() {
		sensor.close();
	}

	@Override
	public void run() {
		while (true) {
			irResponse = finderAdapter.getRange();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
