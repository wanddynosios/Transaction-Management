package discreteEventSimulation.simulator;

import java.util.List;
import java.util.PriorityQueue;

import org.apache.commons.math3.distribution.RealDistribution;

import discreteEventSimulation.event.Event;
import lombok.Getter;
import lombok.ToString;

@ToString
public class Scheduler {
	/**
	 * Represents the current time
	 */
	@Getter
	private long time;
	/**
	 * Represents the events to be executed. Priority: Event with lowest time
	 */
	private PriorityQueue<Event> eventQueue;
	
	@Getter
	private  RealDistribution distribution;
	
	private Integer exeutedEvents;
	
	public Scheduler(RealDistribution distribution)  {
		this.eventQueue = new PriorityQueue<Event>((a, b) -> a.getTimestamp() > b.getTimestamp() ? 1 : -1);
		this.distribution = distribution;
		this.time = 0;
		this.exeutedEvents = 0;
	}
	
	

	/**
	 * Starts scheduler -> executes events in this event queue
	 * 
	 * @param startEvents
	 * @throws Exception 
	 */
	public Integer start(List<Event> startEvents) throws Exception {	
		this.addStartEvents(startEvents);
		Event currentEvent;
		while (!this.eventQueue.isEmpty()) {
			currentEvent = this.eventQueue.remove();
			this.time = currentEvent.getTimestamp();
			currentEvent.execute();
			this.exeutedEvents++;
		}
		return this.exeutedEvents;
	}
	
	private void addStartEvents(List<Event> startEvents) throws Exception {
		long temp = this.time;
		this.time = -1;
		for (Event event : startEvents) {
			this.addEvent(event);
		}
		this.time = temp;
	}
	
	public void stop() {
		this.eventQueue.clear();
	}

	/**
	 * Adds an event. The time of the event must not be in the past of the time of
	 * this scheduler. Sets the the referenced scheduler of the event to this scheduler.
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void addEvent(Event event) throws Exception {
		if (event.getTimestamp() < this.time)
			throw new Exception("Time of event must not be in the past! Scheduler time: " + this.time
					+ ", Event time : " + event.getTimestamp() + ", Event : " + event);
		event.setScheduler(this);
		this.eventQueue.add(event);
	}

	/**
	 * Adds an event. Sets the the referenced scheduler of the event to this scheduler.
	 * 
	 * @param event
	 * @param time  in future, e.g.: scheduler time = 10, time = 5 -> event time =
	 *              15
	 * @throws Exception if time value passed is negative
	 */
	public void addEvent(Event event, long time) throws Exception {
		if (time < 0)
			throw new Exception("Time has to be >= 0 ! Passed : " + time);
		event.setTimestamp(this.time + time);
		event.setScheduler(this);
		this.eventQueue.add(event);
	}
}
