package de.fhdw.tm.des.scheduler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.math3.random.MersenneTwister;

public class Simulator {

	private MersenneTwister rootRandom;
	private ExecutorService executor;
	private List<Future<SimulationResult>> running;
	
	public Simulator() {
		this(new MersenneTwister().nextLong(), Runtime.getRuntime().availableProcessors());
	}

	public Simulator(long mainSeed) {
		this(mainSeed, Runtime.getRuntime().availableProcessors());
	}
	
	public Simulator(long mainSeed, int threads) {
		this.rootRandom = new MersenneTwister(mainSeed);
		executor = Executors.newFixedThreadPool(threads, new SimulationThreadFactory(rootRandom));
		this.running = new LinkedList<Future<SimulationResult>>();
	}
	
	public void terminate() {
		this.executor.shutdown();
	}
	
	public void simulate(Simulation sim) {
		SimulationResult simResult = new SimulationResult(sim);
		Future<SimulationResult> future = this.executor.submit(new SimulationWrapper(sim, simResult), simResult);
		this.running.add(future);
	}
	
	public void simulate(Simulation sim, int count) {
		for (int i = 0; i < count; i++) {
			this.simulate(sim);			
		}
	}
	
	public List<SimulationResult> readResults() throws InterruptedException, ExecutionException {
		List<SimulationResult> result = new LinkedList<SimulationResult>();
		for (Iterator<Future<SimulationResult>> iterator = running.iterator(); iterator.hasNext();) {
			Future<SimulationResult> future = iterator.next();
			SimulationResult currentResult;
			try {
				currentResult = future.get();
			} catch (ExecutionException e) {
				this.executor.shutdownNow();
				throw e;
			}			
			result.add(currentResult);
		}
		return result;
	}
	
}
