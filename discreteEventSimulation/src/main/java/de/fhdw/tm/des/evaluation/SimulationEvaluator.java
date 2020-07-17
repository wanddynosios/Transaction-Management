package de.fhdw.tm.des.evaluation;

import java.util.Properties;

import de.fhdw.tm.des.scheduler.SimulationResult;

public abstract class SimulationEvaluator {
	
	private String name;

	public SimulationEvaluator(String name, Object sut) {
		this.name = name;
		SimulationResult.getResult().register(this, sut);
	}

	public abstract Properties eval();
	
	@Override
	public String toString() {
		return name;
	}

}
