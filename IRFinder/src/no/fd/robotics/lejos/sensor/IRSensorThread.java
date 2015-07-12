package no.fd.robotics.lejos.sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.RangeFinderAdapter;

public class IRSensorThread {

	private EV3IRSensor sensor;
	
	private RangeFinderAdapter rangeFinderAdapter;
	
	public IRSensorThread(Port port) {
		sensor = new EV3IRSensor(port);
		rangeFinderAdapter = new RangeFinderAdapter(sensor.getDistanceMode());		
	}

	public float getRange() {
		try {
			return rangeFinderAdapter.getRange();
		} catch(IndexOutOfBoundsException e) {
			return Float.POSITIVE_INFINITY;
		}
	}

	public void stop() {
		sensor.close();
	}

}
