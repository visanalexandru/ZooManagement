package zoo.animal;

import zoo.habitat.Climate;
import zoo.Rng;

public class RedPanda extends Animal {
    /**
     * The average weight of the red panda is 5 kgs.
     */
    public static final int AVERAGE_WEIGHT = 5000;

    /**
     * Red pandas can live up to 13 years.
     */
    public static final int LIFE_SPAN = 4749;

    /**
     * The average size of a red panda is 55 cm.
     */
    public static final float AVERAGE_SIZE = 55;

    /**
     * The attraction score of the red panda.
     */
    public static final int ATTRACTION_SCORE = 80;

    /**
     * The cost of one red panda.
     */
    public static final int COST = 90;

    /**
     * @return a red panda with a random weight and age.
     */
    public static RedPanda randomRedPanda() {
        return new RedPanda(Rng.getRng().randomNumber(1, LIFE_SPAN),
                (int) Rng.getRng().randomGaussian(AVERAGE_WEIGHT, 1),
                (float) Rng.getRng().randomGaussian(AVERAGE_SIZE, 1)
        );
    }


    private static final String DESCRIPTION =
            "As their name suggests, red pandas’ fur is mostly rusty-red color." +
                    "They have white markings on their ears, cheeks, muzzles and above their eyes.";

    protected RedPanda(String id, int age, int weight, float size, boolean used) {
        super(id, "Red Panda", age, weight, size, AnimalType.MAMMAL, ATTRACTION_SCORE, used);
    }

    public RedPanda(int age, int weight, float size) {
        super("Red Panda", age, weight, size, AnimalType.MAMMAL, ATTRACTION_SCORE);
    }

    /**
     * @return the description of the red panda.
     */
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    /**
     * Red pandas can only eat small birds, say below 15 centimeters.
     */
    @Override
    public boolean hunts(Animal other) {
        return other.type == AnimalType.BIRD && other.getSize() <= 15;
    }

    /**
     * Red pandas can live in the temperate climate zone of the Himalayas.
     */
    @Override
    public boolean canLiveIn(Climate climate) {
        return climate == Climate.TEMPERATE;
    }

    /**
     * @return the cost of a red panda.
     */
    @Override
    public int cost() {
        return COST;
    }
}
