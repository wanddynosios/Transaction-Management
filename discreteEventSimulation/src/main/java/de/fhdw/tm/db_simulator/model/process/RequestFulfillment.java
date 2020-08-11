package model.process;

import model.IO_Callback;
import model.IO_Request;
import vorlesung.version2.modelling.ProcessStep;
import vorlesung.version2.modelling.ProcessStepDelay;

public class RequestFulfillment {
    private final Long delay;
    private IO_Request request;
    private IO_Callback callback;
    private final Long read_write_speed;
    private final String action;

    public RequestFulfillment(IO_Request request, IO_Callback callback, Long read_write_speed, Long delay, String action){
        this.request =  request;
        this.callback = callback;
        this.read_write_speed = read_write_speed;
        this.delay = delay;
        this.action = action;
    }

    @ProcessStepDelay(0)
    public long delay(){
        return delay;
    }

    @ProcessStep(0)
    public void finishedWaiting(){
        System.out.println("Waited " + delay + " to " + action + " request " + request);
    }

    @ProcessStepDelay(1)
    public long reading_writing(){
        return request.getResourceLength() / 4 / read_write_speed;
    }

    @ProcessStep(1)
    public void done(){
        callback.operationComplete(); //TODO und weiter?
        System.out.println("Done " + action + " the request " + request + ". Sending callback " + callback);
    }
}
