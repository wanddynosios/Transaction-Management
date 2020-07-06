package discreteEventSimulation.event;



import discreteEventSimulation.simulator.Scheduler;
import lombok.NoArgsConstructor;

// 2b
@NoArgsConstructor
public class CreateNewEvent extends Event {

	

	public CreateNewEvent(long timestamp, Scheduler scheduler) {
		super(timestamp, scheduler);
	}

	@Override
	public void execute() throws Exception {
		System.out.println(super.scheduler.getTime());
		super.scheduler.addEvent(new CreateNewEvent(), (long) super.scheduler.getDistribution().sample());
	}
}
