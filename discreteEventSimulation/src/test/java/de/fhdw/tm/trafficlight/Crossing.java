package de.fhdw.tm.trafficlight;

import java.util.HashMap;
import java.util.Map;

import de.fhdw.tm.des.modelling.ModelProcess;
import de.fhdw.tm.des.modelling.ProcessStep;
import de.fhdw.tm.des.modelling.ProcessStepDelay;
import de.fhdw.tm.des.scheduler.DESScheduler;

public class Crossing {

	private Map<Integer, TrafficLight> trafficLights;
	private Integer currentLight;
	private Integer greenPhaseTime;
	private Integer redPhaseTime;
	private Integer carLeavingTime;
	private Integer numberOfLights;

	public Crossing(Integer numberOfLights, Integer greenPhaseTime, Integer redPhaseTime, Integer carLeavingTime) {
		this.greenPhaseTime = greenPhaseTime;
		this.redPhaseTime = redPhaseTime;
		this.carLeavingTime = carLeavingTime;
		this.currentLight = 0;
		this.numberOfLights = numberOfLights;
		this.trafficLights = new HashMap<Integer, TrafficLight>();
		for (int i = currentLight; i < numberOfLights; i++) {
			TrafficLight newLight = new TrafficLight(this, this.greenPhaseTime, this.carLeavingTime, i);
			this.trafficLights.put(i, newLight);
			if (i % 2 == 0) {
				DESScheduler.scheduleToFuture(new ModelProcess(new CarArrival(newLight, 20)), 0);
			} else {
				DESScheduler.scheduleToFuture(new ModelProcess(new CarArrival(newLight, 10)), 0);
			}
		}
	}

	@ProcessStepDelay(0)
	public long redPhase() {
		return this.redPhaseTime;
	}

	@ProcessStep(0)
	public void greenPhase() {
		TrafficLight currentTrafficLight = this.trafficLights.get(this.currentLight);
		DESScheduler.log("Start Green Phase: " + currentTrafficLight.toString());
		DESScheduler.scheduleToFuture(new ModelProcess(currentTrafficLight), 0);
	}

	public void nextLight() {
		TrafficLight currentTrafficLight = this.trafficLights.get(this.currentLight);
		DESScheduler.log("End Green Phase: " + currentTrafficLight.toString());
		currentTrafficLight.timeleft = this.greenPhaseTime;
		this.currentLight = ++this.currentLight % numberOfLights;
		DESScheduler.scheduleToFuture(new ModelProcess(this), 0);
	}
}
