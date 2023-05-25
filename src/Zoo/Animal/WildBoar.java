package Zoo.Animal;

import Zoo.Habitat.Climate;
import Zoo.Rng;

public class WildBoar extends Animal {

    /**
     * The average weight of a wild boar is 80 kgs.
     */
    public static int averageWeight = 80000;

    /**
     * Wild boars can live up to 8 years.
     */
    public static int lifeSpan = 2922;

    /**
     * The average size of a wild boar is 90 cm.
     */
    public static float averageSize = 90;


    /**
     * The attraction score of the wild boar.
     */
    public static int attractionScore = 20;

    /**
     * The cost of one wild boar.
     */
    public static int cost = 30;

    /**
     * @return a wild boar with a random weight and age.
     */
    public static WildBoar randomWildBoar() {
        return new WildBoar(Rng.getRng().randomNumber(1, lifeSpan),
                (int) Rng.getRng().randomGaussian(averageWeight, 1),
                (float) Rng.getRng().randomGaussian(averageSize, 1)
        );
    }


    static private final String description = "The wild boar is a bulky, massively built suid with short and relatively thin legs." +
            " The trunk is short and robust, while the hindquarters are comparatively underdeveloped.";

    public WildBoar(int age, int weight, float size) {
        super("Wild Board", age, weight, size, AnimalType.MAMMAL, attractionScore);
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Boars may occasionally prey on small vertebrates.
     */
    @Override
    public boolean hunts(Animal other) {
        return other.getSize() <= 50 && !(other instanceof WildBoar);
    }

    /**
     * Wild boards can live in temperate, tropical or continental climates.
     */
    @Override
    public boolean canLiveIn(Climate climate) {
        return climate == Climate.TEMPERATE || climate == Climate.TROPICAL || climate == Climate.CONTINENTAL;
    }

    /**
     * @return the cost of a wild boar.
     */
    @Override
    public int cost() {
        return cost;
    }

}
