package de.fhdw.tm.trafficlight;

import de.fhdw.tm.des.modelling.ModelProcess;
import de.fhdw.tm.des.modelling.ProcessStep;
import de.fhdw.tm.des.modelling.ProcessStepDelay;
import de.fhdw.tm.des.scheduler.DESScheduler;

public class TrafficLight {

	private Integer waitingCars;
	private Integer carLeavingTime;
	private long timeleft;
	private Integer id;
	private Crossing crossing;

	public TrafficLight(Crossing crossing, long timeleft, Integer carLeavingTime, Integer id) {
		this.id = id;
		this.crossing = crossing;
		this.waitingCars = 0;
		this.timeleft = timeleft;
		this.carLeavingTime = carLeavingTime;
	}

	public void prepareGreenPhase(Integer timeleft) {
		this.timeleft = timeleft;
	}

	public void prepareGreenPhase(Integer timeleft, Integer slowStart) {
		this.timeleft = timeleft;
		if (this.waitingCars > 0) {
			this.waitingCars--;
			this.timeleft = Math.max(this.timeleft - slowStart, 0);
		}
	}

	public void carArrives() {
		this.waitingCars++;
	}

	@ProcessStepDelay(0)
	public long carLeavesDelay() {
		return 0;
	}

	@ProcessStep(0)
	public void carLeaves() {
		if (this.timeleft >= this.carLeavingTime) {
			this.timeleft -= this.carLeavingTime;
			if (this.waitingCars > 0)
				this.waitingCars--;
			DESScheduler.scheduleToFuture(new ModelProcess(this), this.carLeavingTime);
		} else
			this.crossing.nextLight();
	}

	@Override
	public String toString() {
		return "Light = " + this.id + ", waiting cars = " + this.waitingCars + ", time left = " + this.timeleft;
	}
}
