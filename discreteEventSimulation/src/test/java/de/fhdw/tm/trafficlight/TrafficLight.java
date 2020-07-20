package de.fhdw.tm.trafficlight;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import de.fhdw.tm.des.modelling.ModelProcess;
import de.fhdw.tm.des.modelling.ProcessStep;
import de.fhdw.tm.des.modelling.ProcessStepDelay;
import de.fhdw.tm.des.scheduler.DESScheduler;

public class TrafficLight {

	private LinkedList<Vehicle> waitingVehicles;
	private Integer id;
	private long greenUntil,timeleft;

	public TrafficLight(Integer id) {
		this.waitingVehicles = new LinkedList<Vehicle>();
		this.id = id;
	}

	public void prepareGreenPhase(long greenUntil) {
		this.greenUntil = greenUntil;
		this.timeleft = this.greenUntil - DESScheduler.getSimulationTime();
		DESScheduler.log(this.toString());
	}

	public void prepareGreenPhase(long greenUntil, Integer slowStartDelay) throws Exception {
		if (slowStartDelay > greenUntil - DESScheduler.getSimulationTime())
			throw new Exception("Deadlock -> Car can never leave because leaving time is higher than greentime: "
					+ greenUntil + " < " + slowStartDelay);
		this.prepareGreenPhase(greenUntil);

		if (this.waitingVehicles.size() > 0) {
			this.waitingVehicles.getFirst().leavingTime = slowStartDelay;
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
		if (timeleft > 0) {
			try {
				Vehicle next = this.waitingVehicles.getFirst();
				if (next.leavingTime <= timeleft) {
					this.waitingVehicles.removeFirst();
					DESScheduler.scheduleToFuture(new ModelProcess(this), next.leavingTime);
				}
			} catch (NoSuchElementException e) {
				DESScheduler.scheduleToFuture(new ModelProcess(this), 1);
			}
		} else
			DESScheduler.log(this.toString());
	}

	@Override
	public String toString() {
		return "Light = " + this.id + ", waiting cars = " + this.waitingVehicles.size();
	}
}
