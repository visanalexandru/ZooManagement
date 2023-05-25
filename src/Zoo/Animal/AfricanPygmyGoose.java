package Zoo.Animal;

import Zoo.Habitat.Climate;
import Zoo.Rng;

public class AfricanPygmyGoose extends Animal {

    /**
     * The average weight of an african pygmy goose is 300 grams.
     */
    public static int averageWeight = 300;

    /**
     * The average lifespan of an african pygmy is 2000 days.
     */
    public static int lifeSpan = 2000;

    /**
     * The african pygmy goose measures approximately 27 centimeters.
     */
    public static float averageSize = 27;

    /**
     * The attraction score of the african pygmy goose.
     */
    public static int attractionScore = 15;


    /**
     * The cost of one african pygmy goose.
     */
    public static int cost = 10;

    /**
     * @return an african pygmy goose with random characteristics.
     */
    public static AfricanPygmyGoose randomAfricanPygmyGoose() {
        return new AfricanPygmyGoose(
                Rng.getRng().randomNumber(1, lifeSpan),
                (int) Rng.getRng().randomGaussian(averageWeight, 1),
                (float) Rng.getRng().randomGaussian(averageSize, 1));
    }

    /**
     * The description of this animal.
     */
    static private final String description = "The African pygmy goose is a small and colorful waterfowl species found in sub-Saharan Africa. " +
            "It is known for its diminutive size and elegant appearance.";

    public AfricanPygmyGoose(int age, int weight, float size) {
        super("African Pygmy Goose", age, weight, size, AnimalType.BIRD, attractionScore);
    }

    /**
     * @return the description of the african pygmy goose.
     */
    @Override
    public String getDescription() {
        return description;
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
        return cost;
    }
}
