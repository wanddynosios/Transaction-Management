package discreteEventSimulation.simulator;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import discreteEventSimulation.event.CreateNewEventEvent;

public class SchedularTest {


	// 2c
	@Test
	public void runScheduler() {
		Scheduler scheduler = new Scheduler();
		try {
			scheduler.start(Arrays.asList(new CreateNewEventEvent(0, scheduler)));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Scheduler had some error while running!");
		}
	}
	
}
