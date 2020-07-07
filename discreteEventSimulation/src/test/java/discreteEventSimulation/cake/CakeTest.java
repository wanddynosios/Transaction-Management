package discreteEventSimulation.cake;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import discreteEventSimulation.cake.events.Start;
import discreteEventSimulation.event.Event;
import discreteEventSimulation.simulator.Scheduler;

public class CakeTest {

	// 4b
	@Test
	public void bakeMultipleCakes() {
		Cake.setLog(true);
		Integer numberOfCakes = 15;
		try {
			this.bakeCakes(numberOfCakes);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Error");
		}
	}

	// 4c
	@Test
	public void multiSimulation() {
		Cake.setLog(false);
		Integer numberOfCakes = 15, base = 10, maxExponent = 4;
		double totalSd = 0;

		for (int i = 1; i <= maxExponent; i++) {
			totalSd += this.multiSimulationHelper((int) Math.pow(base, i), numberOfCakes);
		}
		System.out.println("Average standard deviation: " + totalSd / maxExponent);
	}

	private double multiSimulationHelper(Integer numberOfSimulations, Integer numberOfCakes) {

		long[] times = new long[numberOfSimulations];

		try {
			for (int i = 0; i < numberOfSimulations; i++) {
				times[i] = this.bakeCakes(numberOfCakes);
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("");
		}

		long total = 0;
		for (int i = 0; i < numberOfSimulations; i++) {
			total += times[i];
		}
		long average = total / numberOfSimulations;

		double sd, acc = 0;
		for (int i = 0; i < numberOfSimulations; i++) {
			acc += Math.pow((times[i] - average), 2);
		}
		sd = Math.round(Math.sqrt(acc / numberOfSimulations - 1));

		System.out.println("Simulations: " + numberOfSimulations + ", Average time for baking " + numberOfCakes
				+ " cakes: " + average + " -> Standard deviation " + sd);
		return sd;
	}

	private long bakeCakes(Integer numberOfCakes) throws Exception {
		Scheduler scheduler = new Scheduler(null);

		List<Event> cakesToBake = new ArrayList<Event>();

		for (int i = 0; i < numberOfCakes; i++) {
			cakesToBake.add(new Start(0, scheduler));
		}

		scheduler.start(cakesToBake);

		return scheduler.getTime();
	}
}
