package de.fhdw.tm.des.evaluation.aggregation;

import org.apache.commons.math3.util.ResizableDoubleArray;

public interface EvaluationPersistantCharacteristic {

	double eval(ResizableDoubleArray data);
	
	String getDesciption();

}
