package Zoo.Animal;

import Zoo.Habitat.Climate;
import Zoo.Rng;

public class WildBoar extends Animal {

    /**
     * The average weight of a wild boar is 80 kgs.
     */
    static int averageWeight = 80000;

    /**
     * Wild boars can live up to 8 years.
     */
    static int lifeSpan = 2922;

    /**
     * The average size of a wild boar is 90 cm.
     */
    static float averageSize = 90;

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
        super("Wild Board", age, weight, size, AnimalType.MAMMAL);
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
        return other.getSize() <= 50;
    }

    /**
     * Wild boards can live in temperate, tropical or continental climates.
     */
    @Override
    public boolean canLiveIn(Climate climate) {
        return climate == Climate.TEMPERATE || climate == Climate.TROPICAL || climate == Climate.CONTINENTAL;
    }

}
