package de.fhdw.tm.process;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import de.fhdw.tm.des.modelling.ModelProcess;
import de.fhdw.tm.des.scheduler.DESScheduler;
import de.fhdw.tm.des.scheduler.Simulation;
import de.fhdw.tm.des.scheduler.Simulator;


public class SomeProcessSimulator {
	@Test
	public void simulate() {
		try {
			this.simulate(true, 0, 1, 1, 200);
		} catch (InterruptedException e) {
			fail(e.getCause());
		}
	}

	private void simulate(boolean debug, long seed, Integer simulations, Integer threads, Integer terminationTime)
			throws InterruptedException {

		DESScheduler.setDebug(debug);

		Simulator simulator = new Simulator(seed, threads);

		Simulation sim = new Simulation() {

			@Override
			public void start() {
			}

			@Override
			public void injectStart() {
				DESScheduler.scheduleToFuture(new ModelProcess(new SomeProcess()), 0);
				DESScheduler.scheduleToFuture(() -> DESScheduler.terminate(), terminationTime);
			}

			@Override
			public void finish() {
			}
		};

		simulator.simulate(sim, simulations);

		simulator.terminate();
		simulator.awaitTermination();
	}
}
