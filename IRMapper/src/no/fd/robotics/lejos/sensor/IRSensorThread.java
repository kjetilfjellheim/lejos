package no.fd.robotics.lejos.sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.RangeFinderAdapter;

public class IRSensorThread {

	private EV3IRSensor sensor;

	private RangeFinderAdapter finderAdapter;		

	public IRSensorThread(Port port, int mode) {
		sensor = new EV3IRSensor(port);
		finderAdapter = new RangeFinderAdapter(sensor.getMode(mode));
	}
	
	public float[] getValue() {
		return finderAdapter.getRanges();
	}

	public void stop() {
		sensor.close();
	}

}
