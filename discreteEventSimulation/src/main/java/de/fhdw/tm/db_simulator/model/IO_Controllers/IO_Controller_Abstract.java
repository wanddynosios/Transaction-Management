package model.IO_Controllers;

import model.*;
import model.basic.CannotHotswapException;
import model.basic.Range;

public abstract class IO_Controller_Abstract implements IO_Controller {
    protected IO_Device[] devices;
    protected Range<Long> range;

    @Override
    public void attachDevices(IO_Device... devices) {
        if (devices == null)
            this.devices = devices;
        else throw new CannotHotswapException("Devices are already set");
    }

    @Override
    public Range<Long> getRange() {
        if (range == null)
            range =  getRangeFromIO_Elements(devices);
        return range;
    }

}
