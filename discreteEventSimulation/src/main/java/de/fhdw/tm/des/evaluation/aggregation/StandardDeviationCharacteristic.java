package de.fhdw.tm.des.evaluation.aggregation;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.util.ResizableDoubleArray;

public class StandardDeviationCharacteristic implements EvaluationPersistantCharacteristic {

	@Override
	public double eval(ResizableDoubleArray data) {
		return data.compute(new StandardDeviation());
	}

	@Override
	public String getDesciption() {
		return "StandardDeviation";
	}

}
