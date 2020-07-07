package discreteEventSimulation.cake.events;

import discreteEventSimulation.cake.Cake;
import discreteEventSimulation.simulator.Scheduler;

public class Mix extends CakeEvent {

	public Mix(long timestamp, Scheduler scheduler, Cake cake) {
		super(timestamp, scheduler, cake);
	}

	@Override
	public void execute() throws Exception {
		super.cake.mix();
	}

}
