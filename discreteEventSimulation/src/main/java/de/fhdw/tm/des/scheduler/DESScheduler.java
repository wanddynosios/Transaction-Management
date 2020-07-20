package de.fhdw.tm.des.scheduler;

import org.apache.commons.math3.random.RandomGenerator;

public abstract class DESScheduler {

	private static boolean debug = false;

	public static void schedule(DESOperation operation, long time) {
		((SimulatorThread) Thread.currentThread()).getScheduler().inject(operation, time);
	}

	public static void scheduleToFuture(DESOperation operation, long time) {
		((SimulatorThread) Thread.currentThread()).getScheduler().injectToFuture(operation, time);
	}

	public static void scheduleToFuture(DESOperation event, double time) {
		((SimulatorThread) Thread.currentThread()).getScheduler().injectToFuture(event, (long) (time + 1));
	}

	public static long getSimulationTime() {
		return ((SimulatorThread) Thread.currentThread()).getScheduler().getTime();
	}

	public static RandomGenerator getRandom() {
		return ((SimulatorThread) Thread.currentThread()).getRandomGenerator();
	}

	public static DESScheduler getScheduler() {
		return ((SimulatorThread) Thread.currentThread()).getScheduler();
	}

	protected abstract void inject(DESOperation operation, long time);

	protected abstract void injectToFuture(DESOperation operation, long time);

	protected abstract long getTime();

	protected abstract void reset();

	public abstract void execute(Simulation sim);

	public static void log(String message) {
		if (debug) {
			System.out.println(getSimulationTime() + " -> " + message);
		}
	}

	public static void log(String message, boolean error) {
		if (debug) {
			if (error) {
				System.err.println(message);
			} else {
				System.out.println(message);
			}
		}
	}

	public static void setDebug(boolean enabled) {
		DESScheduler.debug = enabled;
	}

	public static void terminate() {
		((SimulatorThread) Thread.currentThread()).getScheduler().reset();
	}

}