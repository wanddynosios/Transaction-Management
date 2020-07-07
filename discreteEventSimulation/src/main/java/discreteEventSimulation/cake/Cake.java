package discreteEventSimulation.cake;

import java.util.Random;

import org.apache.commons.math3.distribution.ConstantRealDistribution;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.RandomGeneratorFactory;

import discreteEventSimulation.cake.events.Bake;
import discreteEventSimulation.cake.events.Buy;
import discreteEventSimulation.cake.events.Mix;
import discreteEventSimulation.cake.events.ReadRecipy;
import discreteEventSimulation.cake.events.Weigh;
import discreteEventSimulation.simulator.Scheduler;
import lombok.Setter;

public class Cake {

	private static Integer numberOfCakes = 0;

	@Setter
	private static boolean log = true;

	private static RandomGenerator rng = RandomGeneratorFactory.createRandomGenerator(new Random());

	private static int distRecipyMean = 10;
	private static int distRecipySd = 200;
	private static NormalDistribution distRecipy = new NormalDistribution(rng, distRecipyMean, distRecipySd);

	private static int distBuyMean = 7500;
	private static ExponentialDistribution distBuy = new ExponentialDistribution(rng, distBuyMean);

	private static int distWeighLower = 100;
	private static int distWeighUpper = 200;
	private static UniformRealDistribution distWeigh = new UniformRealDistribution(rng, distWeighLower, distWeighUpper);

	private static int distMixMean = 10;
	private static int distMixySd = 200;
	private static NormalDistribution distMix = new NormalDistribution(rng, distMixMean, distMixySd);

	private static double distBakeValue = 1000;
	private static ConstantRealDistribution distBake = new ConstantRealDistribution(distBakeValue);

	private Integer id;

	private Scheduler scheduler;

	public static void setSeed(long seed) {
		distRecipy.reseedRandomGenerator(seed);
		distBuy.reseedRandomGenerator(seed);
		distWeigh.reseedRandomGenerator(seed);
		distMix.reseedRandomGenerator(seed);
		distBake.reseedRandomGenerator(seed);
	}

	public void start(Scheduler scheduler) throws Exception {
		synchronized (numberOfCakes) {
			this.id = numberOfCakes;
			numberOfCakes++;
		}
		this.scheduler = scheduler;
		long time;
		while ((time = (long) distRecipy.sample()) < 0)
			;
		this.scheduler.addEvent(new ReadRecipy(0, scheduler, this), time);
	}

	public void readRecipy() throws Exception {
		this.logCurrentState("Reading");
		this.scheduler.addEvent(new Buy(0, this.scheduler, this), (long) distBuy.sample());
	}

	public void buy() throws Exception {
		this.logCurrentState("Buying");
		this.scheduler.addEvent(new Weigh(0, this.scheduler, this), (long) distWeigh.sample());
	}

	public void weigh() throws Exception {
		this.logCurrentState("Weighing");
		long time;
		while ((time = (long) distMix.sample()) < 0)
			;
		this.scheduler.addEvent(new Mix(0, this.scheduler, this), time);
	}

	public void mix() throws Exception {
		this.logCurrentState("Mixing");
		this.scheduler.addEvent(new Bake(0, this.scheduler, this), (long) distBake.sample());
	}

	public void bake() {
		this.logCurrentState("Baking");
	}

	private void logCurrentState(String state) {
		if (log)
			System.out.println("Cake : " + this.id + " -> " + state);
	}
}
