package discreteEventSimulation.simulator;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import discreteEventSimulation.event.CreateNewEvent;
import discreteEventSimulation.event.LogTime;

public class SchedularTest {

	// 2c
	@Test
	public void runScheduler() {
		Scheduler scheduler = new Scheduler();
		try {
			scheduler.start(Arrays.asList(new CreateNewEvent(0, scheduler)));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Scheduler had some error while running!");
		}
	}

	// 2d
	@Test
	public void priority() {
		Scheduler scheduler = new Scheduler();
		try {
			scheduler.start(Arrays.asList(new LogTime(2, scheduler),new LogTime(0, scheduler)));
			System.out.print("\n");
			scheduler.start(Arrays.asList(new LogTime(0, scheduler),new LogTime(2, scheduler)));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Scheduler had some error while running!");
		}
	}

}
