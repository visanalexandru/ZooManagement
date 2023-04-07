package Zoo.Animal;

import Zoo.Habitat.Climate;

public class Leopard extends Animal {
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
