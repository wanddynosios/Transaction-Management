package model.IO_Devices;


import model.IO_Callback;
import model.IO_Request;
import model.basic.Range;
import model.process.RequestFulfillment;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import vorlesung.version1.modelling.ModelProcess;

public class HDD extends IO_Device_Abstract {
    private AbstractRealDistribution reactionDistribution;


    public HDD(Range<Long> range, Long readSpeed, Long writeSpeed, Long reactionTime) {
        super(range, readSpeed, writeSpeed, reactionTime);
        reactionDistribution = new UniformRealDistribution(reactionTime * 0.8, reactionTime * 1.2);
    }

    @Override
    public void read(IO_Request request, IO_Callback callback) {
        ModelProcess.scheduleProcess(
                new RequestFulfillment(request, callback, readSpeed, reactionTime, "reading")
        );
    }

    @Override
    public void write(IO_Request request, IO_Callback callback) {
        ModelProcess.scheduleProcess(
                new RequestFulfillment(request, callback, writeSpeed, reactionTime, "writing")
        );
    }

}
