package de.fhdw.tm.des.scheduler;

import java.util.PriorityQueue;

public class DirectScheduler extends DESScheduler {
	
	private PriorityQueue<DESEvent> heap;
	private long currentTime;

	protected DirectScheduler() {
		this.heap = new PriorityQueue<DESEvent>();
		this.currentTime = 0;
	}

	@Override
	protected void inject(DESOperation operation, long time) {
		this.heap.offer(new DESEvent(operation, time)); 
	}

	@Override
	protected void injectToFuture(DESOperation operation, long time) {
		this.heap.offer(new DESEvent(operation, this.currentTime + time));
	}

	@Override
	protected long getTime() {
		return this.currentTime;
	}


	@Override
	protected void reset() {
		this.currentTime = 0;
		this.heap.clear();
	}
	
	@Override
	public void execute(Simulation sim) {
		DESScheduler.getScheduler().reset();
		sim.injectStart();
		sim.start();
			while(! this.heap.isEmpty()) {
				DESEvent event = this.heap.poll();
				this.currentTime = event.getTime();
				event.run();
			}
		sim.finish();
	}

}
