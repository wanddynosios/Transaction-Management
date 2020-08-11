package model;

import model.basic.CannotHotswapException;
import model.basic.Range;

import java.util.Map;


public class ProxyController implements IO_Element {
    private IO_Controller[] controllers;
    private Range<Long> range;

    @Override
    public Range<Long> getRange() {
        if (range == null)
            range = getRangeFromIO_Elements(controllers);
        return range;
    }

    public void attachControllers(IO_Controller... io_controllers) {
        if (controllers == null)
            controllers = io_controllers;
        else throw new CannotHotswapException("Controllers are already set");
    }

    @Override
    public void read(IO_Request request, IO_Callback callback) {
        Map<IO_Element, Range<Long>> toRequest = requestBoundHelper(request, controllers);

        toRequest.forEach((io_controller, longRange) -> io_controller
                .read(new IO_Request(longRange.getMinimum(), longRange.getMaximum() - longRange.getMinimum()), callback));
    }

    @Override
    public void write(IO_Request request, IO_Callback callback) {
        Map<IO_Element, Range<Long>> toRequest = requestBoundHelper(request, controllers);

        toRequest.forEach((io_controller, longRange) -> io_controller
                .write(new IO_Request(longRange.getMinimum(), longRange.getMaximum() - longRange.getMinimum()), callback));
    }

    @Override
    public String toString() {
        return "ProxyController{" +
                "range=" + getRange() +
                '}';
    }
}
