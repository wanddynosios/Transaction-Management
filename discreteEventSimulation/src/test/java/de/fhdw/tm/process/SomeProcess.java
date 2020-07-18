package de.fhdw.tm.process;

import de.fhdw.tm.des.modelling.ProcessStep;
import de.fhdw.tm.des.modelling.ProcessStepDelay;
import de.fhdw.tm.des.scheduler.DESScheduler;

public class SomeProcess {

	@ProcessStepDelay(0)
	public long step0delay() {
		this.log("Step 0 Delay");
		return 10;
	}

	@ProcessStep(0)
	public void step0() {
		this.log("Step 0");
	}

	@ProcessStepDelay(1)
	public long step1delay() {
		this.log("Step 1 Delay");
		return 50;
	}

	@ProcessStep(1)
	public void step1() {
		this.log("Step 1");
	}

	@ProcessStepDelay(2)
	public long step2delay() {
		this.log("Step 2 Delay");
		return 100;
	}

	@ProcessStep(2)
	public void step2() {
		this.log("Step 2");
	}

	private void log(String step) {
		DESScheduler.log(step + " -> " + this.toString());
	}
	
	@Override
	public String toString() {
		return "" + DESScheduler.getSimulationTime();
	}
}
