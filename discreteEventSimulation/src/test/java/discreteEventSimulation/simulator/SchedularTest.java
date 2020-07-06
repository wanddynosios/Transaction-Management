package discreteEventSimulation.simulator;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import discreteEventSimulation.event.Cancel;
import discreteEventSimulation.event.CreateNewEvent;
import discreteEventSimulation.event.LogTime;

public class SchedularTest {

	// 2c,3a,3b,3c
	@Test
	public void runScheduler() {
		Scheduler scheduler = new Scheduler();
		try {
			long stopTime = 1000000;
			System.out.println("Events: Expect ~ " + stopTime / CreateNewEvent.getMean() + ", actual: " + scheduler
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
		Scheduler scheduler = new Scheduler();
		try {
			scheduler.start(Arrays.asList(new LogTime(2, scheduler), new LogTime(0, scheduler)));
			System.out.print("\n");
			scheduler.start(Arrays.asList(new LogTime(0, scheduler), new LogTime(2, scheduler)));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Scheduler had some error while running!");
		}
	}

}
