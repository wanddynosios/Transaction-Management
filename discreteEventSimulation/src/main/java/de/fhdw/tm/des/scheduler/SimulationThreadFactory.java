package de.fhdw.tm.des.scheduler;

import java.util.concurrent.ThreadFactory;

import org.apache.commons.math3.random.MersenneTwister;

public class SimulationThreadFactory implements ThreadFactory {
	
	private MersenneTwister rootRandom;
	private int threadCounter;

	public SimulationThreadFactory(MersenneTwister rootRandom) {
		this.threadCounter = 0;
		this.rootRandom = rootRandom;
	}

	@Override
	public synchronized Thread newThread(Runnable r) {
		return new SimulatorThread(r, this.threadCounter++, rootRandom.nextLong());
	}

}
