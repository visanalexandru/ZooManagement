package zoo.animal;

import zoo.habitat.Climate;
import zoo.Rng;

public class ChineseAlligator extends Animal {
    /**
     * The average weight of a chinese alligator is 30 kg.
     */
    public static int averageWeight = 30000;

    /**
     * The average lifespan of a chinese alligator is 40 years.
     */
    public static int lifeSpan = 14610;

    /**
     * The average length of a chinese alligator is 1.5 meters.
     */
    public static float averageSize = 150;


    /**
     * The attraction score of the chinese alligator.
     */

    public static int attractionScore = 120;

    /**
     * The cost of one chinese alligator.
     */
    public static int cost = 270;


    /**
     * @return a chinese alligator with random characteristics.
     */
    public static ChineseAlligator randomChineseAlligator() {
        return new ChineseAlligator(Rng.getRng().randomNumber(1, lifeSpan),
                (int) Rng.getRng().randomGaussian(averageWeight, 1),
                (float) Rng.getRng().randomGaussian(averageSize, 1)
        );
    }

    /**
     * The description of the chinese alligator.
     */
    static private final String description = "Chinese alligators have a relatively short snout and stout body. " +
            "They possess a dark gray to olive-brown skin color, with a rough texture and prominent scales.";

    public ChineseAlligator(int age, int weight, float size) {
        super("Chinese Alligator", age, weight, size, AnimalType.REPTILE, attractionScore);
    }

    /**
     * @return the description of the chinese alligator.
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Chinese alligators are generally opportunistic feeders, adapting their diet to what is readily available in their environment.
     * They do not hunt animals of the same species.
     */
    @Override
    public boolean hunts(Animal other) {
        return !(other instanceof ChineseAlligator);
    }

    /**
     * The chinese alligator lives in the temperate climate.
     */
    public boolean canLiveIn(Climate climate) {
        return climate == Climate.TEMPERATE;
    }

    /**
     * @return the cost of the chinese alligator.
     */
    @Override
    public int cost() {
        return cost;
    }
}
