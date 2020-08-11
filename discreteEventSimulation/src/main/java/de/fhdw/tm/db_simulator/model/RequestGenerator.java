package model;

import model.process.InitControllers;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import vorlesung.version2.modelling.ModelProcess;
import vorlesung.version2.modelling.ProcessStep;
import vorlesung.version2.modelling.ProcessStepDelay;

import java.util.Random;

public class RequestGenerator {
    private AbstractRealDistribution randomDistribution;
    private ProxyController proxyController;
    private UniformRealDistribution requestStart = new UniformRealDistribution(0l, 190000000l);
    private UniformRealDistribution requestLength = new UniformRealDistribution(0l,  10000000l);
    private Random random = new Random();


    public RequestGenerator(AbstractRealDistribution randomDistribution, IO_Controller controller) {
        this.randomDistribution = randomDistribution;
        proxyController = new InitControllers().init(controller);
    }

    @ProcessStepDelay(0)
    public long startDelay() {
        return (long) randomDistribution.sample();
    }

    @ProcessStep(0)
    public void scheduleNext() {
        if (random.nextInt() % 3 == 0){
            proxyController.write(
                    new IO_Request(
                            Double.valueOf(requestStart.sample()).longValue(),
                            Double.valueOf(requestLength.sample()).longValue()),
                    new IO_Callback_default());
        } else {
            proxyController.read(
                    new IO_Request(
                            Double.valueOf(requestStart.sample()).longValue(),
                            Double.valueOf(requestLength.sample()).longValue()),
                    new IO_Callback_default());
        }
        ModelProcess.scheduleProcess(this);
    }
}
