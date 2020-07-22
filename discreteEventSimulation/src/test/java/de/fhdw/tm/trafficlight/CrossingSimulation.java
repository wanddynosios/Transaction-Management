package de.fhdw.tm.trafficlight;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

import de.fhdw.tm.des.modelling.ModelProcess;
import de.fhdw.tm.des.scheduler.DESScheduler;
import de.fhdw.tm.des.scheduler.Simulation;
import de.fhdw.tm.des.scheduler.Simulator;

public class CrossingSimulation {

	@Test
	public void simulate() {
		try {
			this.simulate(false, 0, 1, 1, 1000000, 4, 20, 5, 2, (20 + 5) / 2, 15, false, 100000, 5000, false);
		} catch (InterruptedException e) {
			fail(e.getCause());
		}
	}

	private void simulate(boolean debug, long seed, Integer simulations, Integer threads, Integer terminationTime,
			Integer numberOfLights, Integer greenPhaseTime, Integer redPhaseTime, Integer vehicleLeavingTime,
			Integer vehicleArrivingMean, Integer slowStartMean, boolean slowStart, Integer chrashMean,
			Integer blockedMean, boolean crashes) throws InterruptedException {

		DESScheduler.setDebug(debug);

		Simulator simulator = new Simulator(seed, threads);

		Simulation sim = new Simulation() {

			@Override
			public void start() {
			}

			@Override
			public void injectStart() {
				DESScheduler.scheduleToFuture(
						new ModelProcess(new Crossing(numberOfLights, greenPhaseTime, redPhaseTime, vehicleLeavingTime,
								vehicleArrivingMean, slowStartMean, slowStart, chrashMean, blockedMean, crashes)),
						0);
				DESScheduler.scheduleToFuture(() -> DESScheduler.terminate(), terminationTime);
			}

			@Override
			public void finish() {
			}
		};

		simulator.simulate(sim, simulations);

		simulator.terminate();
		simulator.awaitTermination();

		try {
			simulator.readResults().forEach(result -> result.printResults());
		} catch (ExecutionException e) {
			fail(e);
		}
	}
}
