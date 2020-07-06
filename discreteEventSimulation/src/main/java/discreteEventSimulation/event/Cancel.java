package discreteEventSimulation.event;

import discreteEventSimulation.simulator.Scheduler;

public class Cancel extends Event {

	public Cancel(long timestamp, Scheduler scheduler) {
		super(timestamp, scheduler);
	}
	
	@Override
	public void execute() throws Exception {
		super.scheduler.stop();
	}

}
