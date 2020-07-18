package de.fhdw.tm.trafficlight;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math3.distribution.ExponentialDistribution;

import de.fhdw.tm.des.modelling.ModelProcess;
import de.fhdw.tm.des.modelling.ProcessStep;
import de.fhdw.tm.des.modelling.ProcessStepDelay;
import de.fhdw.tm.des.scheduler.DESScheduler;

public class Crossing {

	private Map<Integer, TrafficLight> trafficLights;
	private Integer currentLightId;
	private TrafficLight currentTrafficLight;
	private Integer greenPhaseTime;
	private Integer redPhaseTime;
	private Integer carLeavingTime;
	private Integer numberOfLights;
	private Boolean slowStart;
	private ExponentialDistribution slowStartDistribution;

	public Crossing(Integer numberOfLights, Integer greenPhaseTime, Integer redPhaseTime, Integer carLeavingTime,
			Integer slowStartMean, boolean slowStart) {
		this.slowStartDistribution = new ExponentialDistribution(DESScheduler.getRandom(), slowStartMean);
		this.slowStart = slowStart;
		this.greenPhaseTime = greenPhaseTime;
		this.redPhaseTime = redPhaseTime;
		this.carLeavingTime = carLeavingTime;
		this.currentLightId = 0;
		this.numberOfLights = numberOfLights;
		this.trafficLights = new HashMap<Integer, TrafficLight>();
		for (int i = currentLightId; i < numberOfLights; i++) {
			TrafficLight newLight = new TrafficLight(this, this.greenPhaseTime, this.carLeavingTime, i);
			this.trafficLights.put(i, newLight);
			DESScheduler.scheduleToFuture(new ModelProcess(new CarArrival(newLight, greenPhaseTime / carLeavingTime)),
					0);
		}
		this.currentTrafficLight = this.trafficLights.get(this.currentLightId);
	}

	@ProcessStepDelay(0)
	public long redPhase() {
		return 0; // doesnt get called -> see modelprocess -> either as second step or schedule to
		// future with redtime
	}

	@ProcessStep(0)
	public void greenPhase() {

		// preparation of current traffic light
		if (slowStart)
			this.currentTrafficLight.prepareGreenPhase(this.greenPhaseTime, (int) this.slowStartDistribution.sample());
		else
			this.currentTrafficLight.prepareGreenPhase(this.greenPhaseTime);

		DESScheduler.log(
				"Current Time = " + DESScheduler.getSimulationTime() + " -> " + this.currentTrafficLight.toString());

		// schedule current light -> delay represents red phase
		DESScheduler.scheduleToFuture(new ModelProcess(this.currentTrafficLight), this.redPhaseTime);
	}

	public void nextLight() {
		DESScheduler.log(
				"Current Time = " + DESScheduler.getSimulationTime() + " -> " + this.currentTrafficLight.toString());

		// set new id and traffic light object
		this.currentTrafficLight = this.trafficLights
				.get((this.currentLightId = ++this.currentLightId % numberOfLights));

		// reschedule this -> next light becomes green
		DESScheduler.scheduleToFuture(new ModelProcess(this), 0);
	}
}
