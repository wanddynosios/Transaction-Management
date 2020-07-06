package discreteEventSimulation.event;

import discreteEventSimulation.simulator.Scheduler;

public class LogTime extends Event {
	
	public LogTime(long timestamp, Scheduler scheduler) {
		super(timestamp, scheduler);
	}

	@Override
	public void execute() throws Exception {
		System.out.println(super.getScheduler().getTime());
	}

}
