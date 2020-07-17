package de.fhdw.tm.des.modelling;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ModelProcessAccelerator {

private static ModelProcessAccelerator singleton = new ModelProcessAccelerator();
	
	public static ModelProcessAccelerator getSingleton() {
		return ModelProcessAccelerator.singleton;
	}
	
	private HashMap<Class<?>, List<ModelProcessExecutor>> execution;
	
	private ModelProcessAccelerator() {
		this.execution = new HashMap<Class<?>, List<ModelProcessExecutor>>();
	}
	
	public Iterator<ModelProcessExecutor> getProcessIterator(Class<?> c) {
		if(! this.execution.containsKey(c)) {
			this.constructProcessFlow(c);
		}
		return this.execution.get(c).iterator();
	}

	private void constructProcessFlow(Class<?> c) {
		HashMap<Integer, Method> steps = new HashMap<Integer, Method>();
		HashMap<Integer, Method> delays = new HashMap<Integer, Method>();
		
		Method[] methods = c.getMethods();
		for (Method method : methods) {
			Annotation[] annotations = method.getAnnotations();
			for (Annotation annotation : annotations) {
				if(annotation.annotationType().equals(ProcessStep.class)){
					int stepID = ((ProcessStep)annotation).value();
					steps.put(stepID, method);
				}
				if(annotation.annotationType().equals(ProcessStepDelay.class)){
					int stepID = ((ProcessStepDelay)annotation).value();
					delays.put(stepID, method);
				}
			}
		}
		
		List<ModelProcessExecutor> flow = new LinkedList<ModelProcessExecutor>();
		for (int i = 0; i < steps.size(); i++) {
			flow.add(new ModelProcessExecutor(steps.get(i), delays.get(i)));
		}
		
		this.execution.put(c, flow);
	}
	
}
