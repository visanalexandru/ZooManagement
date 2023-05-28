package zoo.animal;

import zoo.habitat.Climate;
import zoo.Rng;

public class WildBoar extends Animal {

    /**
     * The average weight of a wild boar is 80 kgs.
     */
    public static final int AVERAGE_WEIGHT = 80000;

    /**
     * Wild boars can live up to 8 years.
     */
    public static final int LIFESPAN = 2922;

    /**
     * The average size of a wild boar is 90 cm.
     */
    public static final float AVERAGE_SIZE = 90;


    /**
     * The attraction score of the wild boar.
     */
    public static final int ATTRACTION_SCORE = 20;

    /**
     * The cost of one wild boar.
     */
    public static final int COST = 30;

    /**
     * @return a wild boar with a random weight and age.
     */
    public static WildBoar randomWildBoar() {
        return new WildBoar(Rng.getRng().randomNumber(1, LIFESPAN),
                (int) Rng.getRng().randomGaussian(AVERAGE_WEIGHT, 1),
                (float) Rng.getRng().randomGaussian(AVERAGE_SIZE, 1)
        );
    }


    private static final String DESCRIPTION = "The wild boar is a bulky, massively built suid with short and relatively thin legs." +
            " The trunk is short and robust, while the hindquarters are comparatively underdeveloped.";

    protected WildBoar(String id, int age, int weight, float size, boolean used) {
        super(id, "Wild Boar", age, weight, size, AnimalType.MAMMAL, ATTRACTION_SCORE, used);
    }

    public WildBoar(int age, int weight, float size) {
        super("Wild Boar", age, weight, size, AnimalType.MAMMAL, ATTRACTION_SCORE);
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
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
        return COST;
    }

}
