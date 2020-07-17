package de.fhdw.tm.des.scheduler;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import de.fhdw.tm.des.evaluation.SimulationEvaluator;

public class SimulationResult {
	
	private static void setResult(SimulationResult result) {
		((SimulatorThread)Thread.currentThread()).setResult(result);
	}
	
	public static SimulationResult getResult() {
		return ((SimulatorThread)Thread.currentThread()).getResult();
	}

	private Simulation simulation; 
	private HashMap<Class<?>, HashMap<Object, SimulationEvaluator>> resultMap;
	
	public SimulationResult(Simulation sim) {
		this.simulation = sim;
		this.resultMap = new HashMap<Class<?>, HashMap<Object, SimulationEvaluator>>();
	}

	public static void registerResult(SimulationResult result) {
		Thread t = Thread.currentThread();
		if(! (t instanceof SimulatorThread)) {
			throw new SimulationException("Simulation thread type invalid");
		}
		SimulationResult.setResult(result);
	}

	public void register(SimulationEvaluator simulationEvaluator, Object sut) {
		Class<? extends Object> sutClass = sut.getClass();
		HashMap<Object, SimulationEvaluator> classMap = this.resultMap.get(sutClass);
		if (classMap == null) {
			classMap = new HashMap<Object, SimulationEvaluator>();
			this.resultMap.put(sutClass, classMap);
		}
		classMap.put(sut, simulationEvaluator);	
	}
	
	public void printResults() {
		List<Class<?>> classList = this.resultMap.keySet().stream().sorted(Comparator.comparing(Class::toString)).collect(Collectors.toList());
		for (Class<?> c : classList) {
			this.printResults(c);
		}
	}
		
	private void printResults(Class<?> c) {
		List<Object> objectList =  this.resultMap.get(c).keySet().stream().sorted(Comparator.comparing(Object::toString)).collect(Collectors.toList());
		for (Object o : objectList) {
			printResults(c, o);
		}
	}
	
	private void printResults(Class<?> c, Object o) {
		SimulationEvaluator e = this.resultMap.get(c).get(o);
		System.out.println(c.toString() + ": " + o.toString() + ": " + e.toString() + " = " + e.eval());

	}
	
	public Simulation getSimulation() {
		return simulation;
	}
}
