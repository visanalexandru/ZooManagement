package Zoo.Animal;

import Zoo.Habitat.Climate;

public class RedPanda extends Animal {
    static private final String description =
            "As their name suggests, red pandas’ fur is mostly rusty-red color." +
                    "They have white markings on their ears, cheeks, muzzles and above their eyes.";

    public RedPanda(int age, int weight, float size) {
        super("Red Panda", age, weight, size, AnimalType.MAMMAL);
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
