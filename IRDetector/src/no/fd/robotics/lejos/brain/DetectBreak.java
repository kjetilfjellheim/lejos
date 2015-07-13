package no.fd.robotics.lejos.brain;

import java.util.ArrayList;
import java.util.List;

import lejos.hardware.Sound;
import no.fd.robotics.lejos.sensor.IRSensorThread;

public class DetectBreak implements Runnable {

	private IRSensorThread irSensorThread;
	private boolean isRunning = true;

	public DetectBreak(IRSensorThread irSensorThread) {
		this.irSensorThread = irSensorThread;
	}
	List<Float> defaultRanges = new ArrayList<Float>();
	float defaultRange = 0f;
	
	@Override
	public void run() {
		float defaultRange = Float.POSITIVE_INFINITY;
		while (isRunning) {
			float[] response = irSensorThread.getValue();
			if (response != null) {				
				if (response[1] != Float.POSITIVE_INFINITY && defaultRanges.size() <= 100) {
					defaultRanges.add(response[1]);				
				} else if (defaultRange != 0f) {
					for (float f : defaultRanges) {
						defaultRange += f;
					}
					defaultRange /= 100;
				} else {
					float absVal = Math.abs(defaultRange - response[1]);
					if (absVal > 5f) {
						Sound.buzz();
					}
				}
			}
		}
	}

}
