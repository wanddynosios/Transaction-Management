package discreteEventSimulation.event;

import java.util.Random;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.random.RandomGeneratorFactory;

import discreteEventSimulation.simulator.Scheduler;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 2b
@NoArgsConstructor
public class CreateNewEvent extends Event {

	@Getter
	private static Integer mean = 10;
	private static ExponentialDistribution exp = new ExponentialDistribution(
			RandomGeneratorFactory.createRandomGenerator(new Random()), mean);

	public CreateNewEvent(long timestamp, Scheduler scheduler) {
		super(timestamp, scheduler);
	}

	@Override
	public void execute() throws Exception {
		System.out.println(super.scheduler.getTime());
		super.scheduler.addEvent(new CreateNewEvent(), (long) exp.sample());
	}
}
