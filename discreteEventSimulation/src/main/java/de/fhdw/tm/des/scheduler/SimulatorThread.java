package de.fhdw.tm.des.scheduler;

import org.apache.commons.math3.random.MersenneTwister;

public class SimulatorThread extends Thread {
	
	private static final String THREAD_NAME = "Simulator thread";

	private MersenneTwister random;
	private DESScheduler scheduler;
	private SimulationResult result;
	
	public SimulatorThread(Runnable r, int id, long seed) {
		super(r, THREAD_NAME + " " + id);
		this.random = new MersenneTwister(seed);
		this.scheduler = new DirectScheduler();
	}
	
	public MersenneTwister getRandomGenerator() {
		return random;
	}
	
	public DESScheduler getScheduler() {
		return scheduler;
	}
	
	public SimulationResult getResult() {
		return result;
	}
	
	protected void setResult(SimulationResult result) {
		this.result = result;
	}
	
}
