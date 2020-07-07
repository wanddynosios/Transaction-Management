package discreteEventSimulation.cake.events;

import discreteEventSimulation.cake.Cake;
import discreteEventSimulation.simulator.Scheduler;

public class ReadRecipy extends CakeEvent {

	public ReadRecipy(long timestamp, Scheduler scheduler, Cake cake) {
		super(timestamp, scheduler, cake);
	}

	@Override
	public void execute() throws Exception {
		super.cake.readRecipy();
	}

}
