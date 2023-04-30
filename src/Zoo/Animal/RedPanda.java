package Zoo.Animal;

import Zoo.Habitat.Climate;
import Zoo.Rng;

public class RedPanda extends Animal {
    /**
     * The average weight of the red panda is 5 kgs.
     */
    public static int averageWeight = 5000;

    /**
     * Red pandas can live up to 13 years.
     */
    public static int lifeSpan = 4749;

    /**
     * The average size of a red panda is 55 cm.
     */
    public static float averageSize = 55;

    /**
     * The attraction score of the red panda.
     */
    public static int attractionScore = 80;

    /**
     * @return a red panda with a random weight and age.
     */
    public static RedPanda randomRedPanda() {
        return new RedPanda(Rng.getRng().randomNumber(1, lifeSpan),
                (int) Rng.getRng().randomGaussian(averageWeight, 1),
                (float) Rng.getRng().randomGaussian(averageSize, 1)
        );
    }


    static private final String description =
            "As their name suggests, red pandas’ fur is mostly rusty-red color." +
                    "They have white markings on their ears, cheeks, muzzles and above their eyes.";

    public RedPanda(int age, int weight, float size) {
        super("Red Panda", age, weight, size, AnimalType.MAMMAL, attractionScore);
    }

    /**
     * @return the description of the red panda.
     */
    @Override
    public String getDescription() {
        return description;
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
}
