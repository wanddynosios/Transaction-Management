package model.IO_Controllers;

import model.*;
import model.IO_Request;
import model.basic.Range;
import java.util.Map;

public class DirectController extends IO_Controller_Abstract {

    @Override
    public void read(IO_Request request, IO_Callback callback) {
        Map<IO_Element, Range<Long>> toRequest = requestBoundHelper(request, this.devices);

        toRequest.forEach((io_controller, longRange) -> io_controller
                .read(new IO_Request(longRange.getMinimum(), longRange.getMaximum() - longRange.getMinimum()), callback));
    }

    @Override
    public void write(IO_Request request, IO_Callback callback) {
        Map<IO_Element, Range<Long>> toRequest = requestBoundHelper(request, this.devices);

        toRequest.forEach((io_controller, longRange) -> io_controller
                .write(new IO_Request(longRange.getMinimum(), longRange.getMaximum() - longRange.getMinimum()), callback));
    }
}
