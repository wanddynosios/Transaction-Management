package de.fhdw.tm.des.evaluation;

import java.util.Properties;

public class EvaluationCounter extends SimulationEvaluator {

	private int counter;

	public EvaluationCounter(String name, Object sut) {
		super(name, sut);
		this.counter = 0;
	}
	
	public void trigger() {
		this.counter++;
	}

	@Override
	public Properties eval() {
		Properties result = new Properties();
		result.put("Absolute", this.counter);
		return result;
	}

}
