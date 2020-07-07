package discreteEventSimulation.cake.events;

import discreteEventSimulation.cake.Cake;
import discreteEventSimulation.event.Event;
import discreteEventSimulation.simulator.Scheduler;

public abstract class CakeEvent extends Event {
	Cake cake;
	
	public CakeEvent(long timestamp, Scheduler scheduler, Cake cake) {
		super(timestamp, scheduler);
		this.cake = cake;
	}
}
