package de.fhdw.tm.des.scheduler;

public class SimulationWrapper implements Runnable {

	private Simulation simulation;
	private SimulationResult result;

	public SimulationWrapper(Simulation sim, SimulationResult result) {
		this.simulation = sim;
		this.result = result;
	}
	
	@Override
	public void run() {
		Thread t = Thread.currentThread();
		if(! (t instanceof SimulatorThread)) {
			throw new SimulationException("Simulation thread type invalid");
		}
		SimulationResult.registerResult(this.result);
		DESScheduler.getScheduler().execute(this.simulation);
	}
	
	public Simulation getSimulation() {
		return simulation;
	}
	
	public SimulationResult getResult() {
		return result;
	}
}
