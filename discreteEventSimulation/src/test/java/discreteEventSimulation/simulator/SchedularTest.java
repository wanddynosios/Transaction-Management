package discreteEventSimulation.simulator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.RandomGeneratorFactory;
import org.junit.jupiter.api.Test;

import discreteEventSimulation.event.Cancel;
import discreteEventSimulation.event.CreateNewEvent;
import discreteEventSimulation.event.LogTime;

public class SchedularTest {

	// 2c,3a,3b,3c
	@Test
	public void runScheduler() {
		double mean = 10;
		long seed = 42;
		Scheduler scheduler = new Scheduler(
				new ExponentialDistribution(RandomGeneratorFactory.createRandomGenerator(new Random(seed)), mean));
		try {
			long stopTime = 1000000;
			System.out.println("Events: Expect ~ " + stopTime / mean + ", actual: " + scheduler
					.start(Arrays.asList(new CreateNewEvent(0, scheduler), new Cancel(stopTime, scheduler))));
			;
		} catch (Exception e) {
			e.printStackTrace();
			fail("Scheduler had some error while running!");
		}
	}

	// 2d
	@Test
	public void priority() {
		double mean = 10;
		long seed = 42;
		Scheduler scheduler = new Scheduler(
				new ExponentialDistribution(RandomGeneratorFactory.createRandomGenerator(new Random(seed)), mean));

		try {
			scheduler.start(Arrays.asList(new LogTime(2, scheduler), new LogTime(0, scheduler)));
			System.out.print("\n");
			scheduler.start(Arrays.asList(new LogTime(0, scheduler), new LogTime(2, scheduler)));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Scheduler had some error while running!");
		}
	}

	// 3d
	@Test
	public List<Integer> multiSimulation() {
		Scheduler scheduler;
		Integer simulators = 10;
		List<Integer> executedEvents = new ArrayList<Integer>();
		double mean = 10;
		long stopTime = 1000;
		RandomGenerator randomSeeds = RandomGeneratorFactory.createRandomGenerator(new Random(42));
		try {
			for (int i = 0; i < simulators; i++) {
				scheduler = new Scheduler(new ExponentialDistribution(
						RandomGeneratorFactory.createRandomGenerator(new Random(randomSeeds.nextInt())), mean));

				executedEvents.add(scheduler
						.start(Arrays.asList(new CreateNewEvent(0, scheduler), new Cancel(stopTime, scheduler))));
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Scheduler had some error while running!");
		}
		System.out.println(executedEvents);
		return executedEvents;
	}

	// 3d
	@Test
	public void repeatedMultiSimulation() {
		Integer numberOfMultiSimulations = 5;
		List<List<Integer>> simulations = new ArrayList<List<Integer>>();
		for (int i = 0; i < numberOfMultiSimulations; i++) {
			simulations.add(this.multiSimulation());
		}
		for (int i = 0; i < numberOfMultiSimulations - 1; i++) {
			assertArrayEquals(simulations.get(i).toArray(), simulations.get(i+1).toArray());
		}
	}
}
