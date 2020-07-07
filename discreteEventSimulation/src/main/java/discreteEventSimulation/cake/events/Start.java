package discreteEventSimulation.cake.events;

import discreteEventSimulation.cake.Cake;
import discreteEventSimulation.event.Event;
import discreteEventSimulation.simulator.Scheduler;

public class Start extends Event {

	public Start(long timestamp, Scheduler scheduler) {
		super(timestamp, scheduler);
	}

	@Override
	public void execute() throws Exception {
		Cake cake = new Cake();
		cake.start(super.getScheduler());
	}

}
