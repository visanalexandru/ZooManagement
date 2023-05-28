package zoo.animal;

import zoo.habitat.Climate;
import zoo.Rng;

public class AfricanPygmyGoose extends Animal {

    /**
     * The average weight of an african pygmy goose is 300 grams.
     */
    public static final int AVERAGE_WEIGHT = 300;

    /**
     * The average lifespan of an african pygmy is 2000 days.
     */
    public static final int LIFE_SPAN = 2000;

    /**
     * The african pygmy goose measures approximately 27 centimeters.
     */
    public static final float AVERAGE_SIZE = 27;

    /**
     * The attraction score of the african pygmy goose.
     */
    public static final int ATTRACTION_SCORE = 15;


    /**
     * The cost of one african pygmy goose.
     */
    public static final int COST = 10;

    /**
     * @return an african pygmy goose with random characteristics.
     */
    public static AfricanPygmyGoose randomAfricanPygmyGoose() {
        return new AfricanPygmyGoose(
                Rng.getRng().randomNumber(1, LIFE_SPAN),
                (int) Rng.getRng().randomGaussian(AVERAGE_WEIGHT, 1),
                (float) Rng.getRng().randomGaussian(AVERAGE_SIZE, 1));
    }

    /**
     * The description of this animal.
     */
    private static final String DESCRIPTION = "The African pygmy goose is a small and colorful waterfowl species found in sub-Saharan Africa. " +
            "It is known for its diminutive size and elegant appearance.";

    protected AfricanPygmyGoose(String id, int age, int weight, float size, boolean used) {
        super(id, "African Pygmy Goose", age, weight, size, AnimalType.BIRD, ATTRACTION_SCORE, used);
    }

    public AfricanPygmyGoose(int age, int weight, float size) {
        super("African Pygmy Goose", age, weight, size, AnimalType.BIRD, ATTRACTION_SCORE);
    }

    /**
     * @return the description of the african pygmy goose.
     */
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }


    /**
     * African pygmy geese are herbivorous birds and are especially fond of consuming grasses, sedges,
     * water lilies, and other aquatic plants.
     */
    @Override
    public boolean hunts(Animal other) {
        return false;
    }

    /**
     * African pygmy geese live in tropical climates.
     */
    @Override
    public boolean canLiveIn(Climate climate) {
        return climate == Climate.TROPICAL;
    }

    @Override
    public int cost() {
        return COST;
    }
}
