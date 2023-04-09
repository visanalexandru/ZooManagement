package Zoo.Animal;

import Zoo.Habitat.Climate;
import Zoo.Rng;

public class Leopard extends Animal {

    /**
     * The average weight of a leopard is 35 kg.
     */
    public static int averageWeight = 35000;

    /**
     * Leopards can live up to 17 years.
     */
    public static int lifeSpan = 6210;

    /**
     * The average size of a leopard is 210 cm.
     */
    public static float averageSize = 210;

    /**
     * @return a leopard with a random weight and age.
     */
    public static Leopard randomLeopard() {
        return new Leopard(Rng.getRng().randomNumber(1, lifeSpan),
                (int) Rng.getRng().randomGaussian(averageWeight, 1),
                (float) Rng.getRng().randomGaussian(averageSize, 1)
        );
    }

    static private final String description =
            "Compared to other wild cats, the leopard has relatively short legs and a long body with a large skull. " +
                    "Its fur is marked with rosettes. " +
                    "It is similar in appearance to the jaguar.";

    public Leopard(int age, int weight, float size) {
        super("Leopard", age, weight, size, AnimalType.MAMMAL);
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * The leopard prefers medium-sized prey with a body mass ranging from 0–40 kg.
     */
    @Override
    public boolean hunts(Animal other) {
        return other.getWeight() <= 40000;
    }

    /**
     * Leopards have a very wide habitat tolerance.
     */
    @Override
    public boolean canLiveIn(Climate climate) {
        return climate != Climate.POLAR;
    }
}
