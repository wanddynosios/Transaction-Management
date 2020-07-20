package de.fhdw.tm.trafficlight;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import de.fhdw.tm.des.evaluation.EvaluationInterval;
import de.fhdw.tm.des.evaluation.SimulationEvaluatorWithStore;
import de.fhdw.tm.des.evaluation.aggregation.CountCharacteristic;
import de.fhdw.tm.des.evaluation.aggregation.MeanCharacteristic;
import de.fhdw.tm.des.evaluation.aggregation.StandardDeviationCharacteristic;
import de.fhdw.tm.des.modelling.ModelProcess;
import de.fhdw.tm.des.modelling.ProcessStep;
import de.fhdw.tm.des.modelling.ProcessStepDelay;
import de.fhdw.tm.des.scheduler.DESScheduler;

public class TrafficLight {

	private LinkedList<Vehicle> waitingVehicles;
	private Integer id;
	private long greenUntil, timeleft;
	private String start;
	private EvaluationInterval vehicleWaiting;
	private SimulationEvaluatorWithStore vehicleQueue;
	private Crossing crossing;

	public TrafficLight(Integer id, Crossing crossing) {
		this.waitingVehicles = new LinkedList<Vehicle>();
		this.id = id;
		this.crossing = crossing;

		this.vehicleWaiting = new EvaluationInterval("Vehicle waiting", this, new MeanCharacteristic(),
				new CountCharacteristic(), new StandardDeviationCharacteristic());
		this.vehicleWaiting.intervalStart();

		this.vehicleQueue = new SimulationEvaluatorWithStore("Light " + this.id + " -> Vehicle queue", new Object(),
				new MeanCharacteristic(), new CountCharacteristic(), new StandardDeviationCharacteristic()) {
		};
	}

	public void prepareGreenPhase(long greenUntil) {
		this.vehicleQueue.addData(this.waitingVehicles.size());
		this.greenUntil = greenUntil;
		this.timeleft = this.greenUntil - DESScheduler.getSimulationTime();
		this.start = this.print();
	}

	public void prepareGreenPhase(long greenUntil, Integer slowStartDelay) {
		this.prepareGreenPhase(greenUntil);
		if (this.waitingVehicles.size() > 0) {
			this.waitingVehicles.getFirst().leavingTime = (int) Math.min(greenUntil - DESScheduler.getSimulationTime(),
					slowStartDelay);
		}
	}

	public void pushVehicle(Vehicle vehicle) {
		this.waitingVehicles.addLast(vehicle);
	}

	@ProcessStepDelay(0)
	public long popVehicleDelay() {
		return 0; // doesnt get called -> see implementation modelling
	}

	@ProcessStep(0)
	public void popVehicle() {

		this.timeleft = this.greenUntil - DESScheduler.getSimulationTime();
		
		if (this.timeleft > 0) {

			if (this.crossing.blocked)
				this.passTime(1);
			else {

				try {
					Vehicle next = this.waitingVehicles.getFirst();
					if (next.leavingTime <= timeleft) {
						this.waitingVehicles.removeFirst();
						this.vehicleWaiting.trigger();
						// first vehicle leaving
						DESScheduler.scheduleToFuture(new ModelProcess(this), next.leavingTime);
					}
				} catch (NoSuchElementException e) {
					// no vehicle waiting
					this.passTime(1);
				}
			}
		} else
			DESScheduler.log(this.start + " -> " + this.print());
	}

	private void passTime(long time) {
		DESScheduler.scheduleToFuture(new ModelProcess(this), time);
	}

	public String print() {
		return DESScheduler.getSimulationTime() + ": " + "Light = " + this.id + ", waiting cars = "
				+ this.waitingVehicles.size();
	}

	@Override
	public String toString() {
		return this.id.toString();
	}
}
