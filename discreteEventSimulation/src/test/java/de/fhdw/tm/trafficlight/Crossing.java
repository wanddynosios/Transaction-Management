package de.fhdw.tm.trafficlight;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math3.distribution.ExponentialDistribution;

import de.fhdw.tm.des.evaluation.EvaluationInterval;
import de.fhdw.tm.des.evaluation.aggregation.CountCharacteristic;
import de.fhdw.tm.des.evaluation.aggregation.MeanCharacteristic;
import de.fhdw.tm.des.evaluation.aggregation.StandardDeviationCharacteristic;
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
	private Integer numberOfLights;
	private Boolean slowStart;
	private ExponentialDistribution slowStartDistribution;
	private EvaluationInterval greenLightStats;
	private EvaluationInterval redLightStats;
	Integer crashes;

	public Crossing(Integer numberOfLights, Integer greenPhaseTime, Integer redPhaseTime, Integer vehicleLeavingTime,
			Integer vehicleArrivingMean, Integer slowStartMean, boolean slowStart, Integer chrashMean,
			Integer blockedMean, boolean crashes) {
		this.slowStartDistribution = new ExponentialDistribution(DESScheduler.getRandom(), slowStartMean);
		this.slowStart = slowStart;
		this.greenPhaseTime = greenPhaseTime;
		this.redPhaseTime = redPhaseTime;
		this.currentLightId = 0;
		this.numberOfLights = numberOfLights;
		this.crashes = 0;
		this.trafficLights = new HashMap<Integer, TrafficLight>();
		for (int i = currentLightId; i < numberOfLights; i++) {
			TrafficLight newLight = new TrafficLight(i,this);
			this.trafficLights.put(i, newLight);
			DESScheduler.scheduleToFuture(
					new ModelProcess(new VehicleArrival(newLight, vehicleArrivingMean, vehicleLeavingTime)), 0);
		}

		if (crashes)
			DESScheduler.scheduleToFuture(new ModelProcess(new Crash(chrashMean, blockedMean, this)), 0);

		this.currentTrafficLight = this.trafficLights.get(this.currentLightId);

		this.redLightStats = new EvaluationInterval("Red Light Phases", new Object(), new MeanCharacteristic(),
				new CountCharacteristic(), new StandardDeviationCharacteristic());

		this.greenLightStats = new EvaluationInterval("Green Light Phases", new Object(), new MeanCharacteristic(),
				new CountCharacteristic(), new StandardDeviationCharacteristic());
	}

	@ProcessStepDelay(0)
	public long setUpDelay() {
		return 0;
	}

	@ProcessStep(0)
	public void setUp() {
		this.currentTrafficLight = this.trafficLights
				.get((this.currentLightId = ++this.currentLightId % numberOfLights));
	}

	@ProcessStepDelay(1)
	public long greenPhaseTime() {
		this.greenLightStats.intervalStart();
		if (slowStart)
			this.currentTrafficLight.prepareGreenPhase(DESScheduler.getSimulationTime() + this.greenPhaseTime,
					(int) this.slowStartDistribution.sample());
		else
			this.currentTrafficLight.prepareGreenPhase(DESScheduler.getSimulationTime() + this.greenPhaseTime);

		DESScheduler.scheduleToFuture(new ModelProcess(this.currentTrafficLight), 0);

		return this.greenPhaseTime;
	}

	@ProcessStep(1)
	public void greenPhase() {
		this.greenLightStats.intervalStop();
	}

	@ProcessStepDelay(2)
	public long redPhaseTime() {
		this.redLightStats.intervalStart();
		return this.redPhaseTime;
	}

	@ProcessStep(2)
	public void redPhase() {
		this.redLightStats.intervalStop();
		DESScheduler.scheduleToFuture(new ModelProcess(this), 0);
	}

	@Override
	public String toString() {
		return "number of lights = " + this.numberOfLights + ", slowstart = " + this.slowStart;
	}
	
	public void crash() {
		this.crashes++;
	}
	
	public void removeCrash() {
		this.crashes--;
	}
}
