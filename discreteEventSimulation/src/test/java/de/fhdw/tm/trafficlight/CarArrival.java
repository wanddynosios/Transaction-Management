package de.fhdw.tm.trafficlight;

import org.apache.commons.math3.distribution.ExponentialDistribution;

import de.fhdw.tm.des.modelling.ModelProcess;
import de.fhdw.tm.des.modelling.ProcessStep;
import de.fhdw.tm.des.modelling.ProcessStepDelay;
import de.fhdw.tm.des.scheduler.DESScheduler;

public class CarArrival {

	private TrafficLight trafficLight;
	private ExponentialDistribution distribution;
	private Integer carLeavingTime;

	public CarArrival(TrafficLight trafficLight, Integer mean, Integer carLeavingTime) {
		this.carLeavingTime = carLeavingTime;
		this.trafficLight = trafficLight;
		this.distribution = new ExponentialDistribution(DESScheduler.getRandom(), mean);
	}

	@ProcessStepDelay(0)
	public long nextCarDelay() {
		return 0;
	}

	@ProcessStep(0)
	public void nextCar() {
		this.trafficLight.vehicleArriving(new Vehicle(this.carLeavingTime));
		DESScheduler.scheduleToFuture(new ModelProcess(this), (long) this.distribution.sample());
	}
}
