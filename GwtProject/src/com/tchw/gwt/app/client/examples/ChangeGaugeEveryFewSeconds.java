package com.tchw.gwt.app.client.examples;

import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.tchw.gwt.app.client.visualizations.VGauge;

public class ChangeGaugeEveryFewSeconds {

	public static void install(final VGauge gauge) {
		Timer timer = new Timer() {
			@Override
			public void run() {
				gauge.setValue(Random.nextInt(100));
			}
		};
		timer.scheduleRepeating(1000);
	}
	
}
