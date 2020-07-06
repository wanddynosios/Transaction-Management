package discreteEventSimulation.event;

import discreteEventSimulation.simulator.Scheduler;
import lombok.NoArgsConstructor;

// 2b
@NoArgsConstructor
public class CreateNewEventEvent extends Event {
	
	public CreateNewEventEvent(long timestamp, Scheduler scheduler) {
		super(timestamp, scheduler);
	}
	
	@Override
	public void execute() throws Exception {
		System.out.println(super.scheduler.getTime());
		super.scheduler.addEvent(new CreateNewEventEvent(), 10);
	}
}
