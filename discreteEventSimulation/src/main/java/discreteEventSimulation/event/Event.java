package discreteEventSimulation.event;

import discreteEventSimulation.command.Command;
import discreteEventSimulation.simulator.Scheduler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = "scheduler")
@AllArgsConstructor
@NoArgsConstructor
public abstract class Event implements Command {
	/**
	 * timestamp, when the event gets executed
	 */
	@Getter
	@Setter
	long timestamp;
	
	/**
	 * reference to its scheduler
	 */
	@Setter
	@Getter
	Scheduler scheduler;
}
