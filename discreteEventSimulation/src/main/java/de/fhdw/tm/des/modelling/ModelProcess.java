package de.fhdw.tm.des.modelling;

import java.util.Iterator;

import de.fhdw.tm.des.scheduler.DESOperation;
import de.fhdw.tm.des.scheduler.DESScheduler;

public class ModelProcess implements DESOperation {
	
	private Object targetProcess;
	private ModelProcessExecutor current;
	private Iterator<ModelProcessExecutor> stepIterator;
	
	public ModelProcess(Object process) {
		this.targetProcess = process;
		this.stepIterator = ModelProcessAccelerator.getSingleton().getProcessIterator(process.getClass());
		this.current = this.stepIterator.next();
	}

	// TODO first process step has no delay
	@Override
	public void process() {
		current.process(this.targetProcess);
		if(this.stepIterator.hasNext()) {
			this.current = this.stepIterator.next();
			DESScheduler.scheduleToFuture(this, this.getDelay());
		}
	}
	
	public long getDelay() {
		return current.getDelay(this.targetProcess);
	}

}
