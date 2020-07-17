package de.fhdw.tm.des.scheduler;

public class DESEvent implements Runnable, Comparable<DESEvent> {
	
	private long executionTime;
	private DESOperation operation;
	
	public DESEvent(DESOperation operation, long executionTime) {
		this.operation = operation;
		this.executionTime = executionTime;
	}
	

	@Override
	public int compareTo(DESEvent o) {
		return (int) (this.executionTime - o.executionTime);
	}

	@Override
	public void run() {
		this.operation.process();
	}
	
	public long getTime() {
		return executionTime;
	}

}