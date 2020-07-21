package de.fhdw.tm.trafficlight;

import org.apache.commons.math3.distribution.ExponentialDistribution;

import de.fhdw.tm.des.modelling.ModelProcess;
import de.fhdw.tm.des.modelling.ProcessStep;
import de.fhdw.tm.des.modelling.ProcessStepDelay;
import de.fhdw.tm.des.scheduler.DESScheduler;

public class Crash {

	private ExponentialDistribution crash, block;
	private Crossing crossing;

	public Crash(Integer crashMean, Integer blockMean, Crossing crossing) {
		this.crossing = crossing;
		this.crash = new ExponentialDistribution(DESScheduler.getRandom(), crashMean);
		this.block = new ExponentialDistribution(DESScheduler.getRandom(), blockMean);
	}

	@ProcessStepDelay(0)
	public long setUpDelay() {
		return 0;
	}

	@ProcessStep(0)
	public void setUp() {

	}

	@ProcessStepDelay(1)
	public long crashDelay() {
		return (long) this.crash.sample();
	}

	@ProcessStep(1)
	public void crash() {
		this.crossing.crash();
	}

	@ProcessStepDelay(2)
	public long removeCrashDelay() {
		return (long) this.block.sample();
	}

	@ProcessStep(2)
	public void removeCrash() {
		this.crossing.removeCrash();
		DESScheduler.schedule(new ModelProcess(this), 0);
	}
}
