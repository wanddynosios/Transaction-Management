package de.fhdw.tm.trafficlight;

import org.apache.commons.math3.distribution.ExponentialDistribution;

import de.fhdw.tm.des.modelling.ModelProcess;
import de.fhdw.tm.des.modelling.ProcessStep;
import de.fhdw.tm.des.modelling.ProcessStepDelay;
import de.fhdw.tm.des.scheduler.DESScheduler;

public class VehicleArrival {

	private TrafficLight trafficLight;
	private ExponentialDistribution distribution;
	private Integer leavingTime;

	public VehicleArrival(TrafficLight trafficLight, Integer mean, Integer leavingTime) {
		this.leavingTime = leavingTime;
		this.trafficLight = trafficLight;
		this.distribution = new ExponentialDistribution(DESScheduler.getRandom(), mean);
	}

	@ProcessStepDelay(0)
	public long nextDelay() {
		return 0;
	}

	@ProcessStep(0)
	public void next() {
		this.trafficLight.pushVehicle(new Vehicle(this.leavingTime, (int) DESScheduler.getSimulationTime()));
		DESScheduler.scheduleToFuture(new ModelProcess(this), (long) this.distribution.sample());
	}
}
