package zoo;

import java.util.Random;

/**
 * This class is a singleton class used for generating random numbers.
 */
public class Rng {
    /**
     * The random number generator.
     */
    private final Random random;

    /**
     * The instance of the singleton class.
     */
    private static Rng rng = null;

    private Rng() {
        random = new Random();
    }

    /**
     * @return the instance of the singleton class.
     */
    public static Rng getRng() {
        if (rng == null) {
            rng = new Rng();
        }
        return rng;
    }

    /**
     * @param a the left bound
     * @param b the right bound
     * @return a random number in the interval [a,b]
     */
    public int randomNumber(int a, int b) {
        return random.nextInt(b - a + 1) + a;
    }

    /**
     * @param deviation the deviation of the distribution.
     * @param mean      the mean of the distribution.
     * @return a random number generated using the given gaussian distribution.
     */
    public double randomGaussian(float mean, float deviation) {
        return random.nextGaussian() * deviation + mean;
    }
}
