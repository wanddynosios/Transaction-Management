package model;


import model.basic.Range;

import java.util.HashMap;
import java.util.Map;

public interface IO_Element {

	Range<Long> getRange();
	public void read(IO_Request request, IO_Callback callback);
	public void write(IO_Request request, IO_Callback callback);

	default Map<IO_Element, Range<Long>> requestBoundHelper(IO_Request request, IO_Element[] elements) {
		long start = request.getResourceStart();
		long length = request.getResourceLength();
		long end = start + length;

		if (!(getRange().contains(start) && getRange().contains(end))){
			throw new ArrayIndexOutOfBoundsException("The Address Range " + start + " - " + end + " is not in " + this);
		}
		Map<IO_Element, Range<Long>> toRequest = new HashMap<IO_Element, Range<Long>>();
		for (IO_Element element : elements){
			if (element.getRange().contains(start) && element.getRange().contains(end)){
				toRequest.put(element, new Range<Long>(start, end));
			} else if (element.getRange().contains(start) && !element.getRange().contains(end)){
				toRequest.put(element, new Range<Long>(start, element.getRange().getMaximum()));
			} else if (!element.getRange().contains(start) && element.getRange().contains(end)) {
				toRequest.put(element, new Range<Long>(element.getRange().getMinimum(), end));
			}
		}
		return toRequest;
	}

	default Range<Long> getRangeFromIO_Elements(IO_Element... elements){
		Long minimum = null;
		Long maximum = null;
		for (IO_Element element: elements){
			if (minimum == null)
				minimum = element.getRange().getMinimum();
			else
			if (element.getRange().getMinimum() < minimum)
				minimum = element.getRange().getMinimum();
			if (maximum == null)
				maximum = element.getRange().getMaximum();
			else
			if (element.getRange().getMaximum() > maximum)
				maximum = element.getRange().getMaximum();
		}
		return new Range<Long>(minimum, maximum);
	}
}
