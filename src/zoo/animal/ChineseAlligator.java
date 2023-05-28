package zoo.animal;

import zoo.habitat.Climate;
import zoo.Rng;

public class ChineseAlligator extends Animal {
    /**
     * The average weight of a chinese alligator is 30 kg.
     */
    public static final int AVERAGE_WEIGHT = 30000;

    /**
     * The average lifespan of a chinese alligator is 40 years.
     */
    public static final int LIFE_SPAN = 14610;

    /**
     * The average length of a chinese alligator is 1.5 meters.
     */
    public static final float AVERAGE_SIZE = 150;


    /**
     * The attraction score of the chinese alligator.
     */

    public static final int ATTRACTION_SCORE = 120;

    /**
     * The cost of one chinese alligator.
     */
    public static final int COST = 270;


    /**
     * @return a chinese alligator with random characteristics.
     */
    public static ChineseAlligator randomChineseAlligator() {
        return new ChineseAlligator(Rng.getRng().randomNumber(1, LIFE_SPAN),
                (int) Rng.getRng().randomGaussian(AVERAGE_WEIGHT, 1),
                (float) Rng.getRng().randomGaussian(AVERAGE_SIZE, 1)
        );
    }

    /**
     * The description of the chinese alligator.
     */
    private static final String DESCRIPTION = "Chinese alligators have a relatively short snout and stout body. " +
            "They possess a dark gray to olive-brown skin color, with a rough texture and prominent scales.";

    protected ChineseAlligator(String id, int age, int weight, float size, boolean used) {
        super(id, "Chinese Alligator", age, weight, size, AnimalType.REPTILE, ATTRACTION_SCORE, used);
    }

    public ChineseAlligator(int age, int weight, float size) {
        super("Chinese Alligator", age, weight, size, AnimalType.REPTILE, ATTRACTION_SCORE);
    }

    /**
     * @return the description of the chinese alligator.
     */
    @Override
    public String getDescription() {
        return DESCRIPTION;
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
        return COST;
    }
}
