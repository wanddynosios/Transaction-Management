package discreteEventSimulation.random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Random;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.RandomGeneratorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 * 
 * 1b, 1c
 *
 */
@TestInstance(Lifecycle.PER_CLASS)
public class RandomTest {

	public Integer numberOfValues;

	@BeforeAll
	public void init() {
		this.numberOfValues = 1000;
	}

	@Test
	public void compareGeneratedValuesWithSameSeed() {
		long seed = 42;
		long[] values1 = new long[this.numberOfValues];
		long[] values2 = new long[this.numberOfValues];

		RandomGenerator g1 = RandomGeneratorFactory.createRandomGenerator(new Random(seed)),
				g2 = RandomGeneratorFactory.createRandomGenerator(new Random(seed));

		for (int i = 0; i < 10; i++) {
			values1[i] = g1.nextLong();
			values2[i] = g2.nextLong();
		}

		assertArrayEquals(values1, values2);
	}

	@Test
	public void compareGeneratedValuesWithDifferentSeed() {
		long seed1 = 42, seed2 = 5;

		long[] values1 = new long[this.numberOfValues];
		long[] values2 = new long[this.numberOfValues];

		RandomGenerator g1 = RandomGeneratorFactory.createRandomGenerator(new Random(seed1)),
				g2 = RandomGeneratorFactory.createRandomGenerator(new Random(seed2));

		for (int i = 0; i < 10; i++) {
			values1[i] = g1.nextLong();
			values2[i] = g2.nextLong();
		}

		assertArrayNotEquals(values1, values2);
	}

	private void assertArrayNotEquals(long[] expecteds, long[] actuals) {
		try {
			assertArrayEquals(expecteds, actuals);
		} catch (AssertionError e) {
			return;
		}
		fail("The arrays are equal");
	}

}
