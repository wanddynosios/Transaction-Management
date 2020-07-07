package discreteEventSimulation.cake;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import discreteEventSimulation.cake.events.Start;
import discreteEventSimulation.event.Event;
import discreteEventSimulation.simulator.Scheduler;

public class CakeTest {

	@Test
	public void bakeMultipleCakes() {
		Integer numberOfCakes = 15;
		Scheduler scheduler = new Scheduler(null);
		
		List<Event> cakesToBake = new ArrayList<Event>();
		
		for (int i = 0; i < numberOfCakes; i++) {
			cakesToBake.add(new Start(0, scheduler));
		}
		

		try {
			scheduler.start(cakesToBake);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Cakes failed to bake");
		}
	}
}
