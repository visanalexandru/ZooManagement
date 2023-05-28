package zoo.animal;

import zoo.habitat.Climate;
import zoo.Rng;

public class Leopard extends Animal {

    /**
     * The average weight of a leopard is 35 kg.
     */
    public static final int AVERAGE_WEIGHT = 35000;

    /**
     * Leopards can live up to 17 years.
     */
    public static final int LIFE_SPAN = 6210;

    /**
     * The average size of a leopard is 210 cm.
     */
    public static final float AVERAGE_SIZE = 210;

    /**
     * The attraction score of the leopard.
     */
    public static final int ATTRACTION_SCORE = 30;

    /**
     * The cost of one leopard.
     */
    public static final int COST = 100;

    /**
     * @return a leopard with a random weight and age.
     */
    public static Leopard randomLeopard() {
        return new Leopard(Rng.getRng().randomNumber(1, LIFE_SPAN),
                (int) Rng.getRng().randomGaussian(AVERAGE_WEIGHT, 1),
                (float) Rng.getRng().randomGaussian(AVERAGE_SIZE, 1)
        );
    }

    private static final String DESCRIPTION =
            "Compared to other wild cats, the leopard has relatively short legs and a long body with a large skull. " +
                    "Its fur is marked with rosettes. " +
                    "It is similar in appearance to the jaguar.";

    protected Leopard(String id, int age, int weight, float size, boolean used) {
        super(id, "Leopard", age, weight, size, AnimalType.MAMMAL, ATTRACTION_SCORE, used);
    }

    public Leopard(int age, int weight, float size) {
        super("Leopard", age, weight, size, AnimalType.MAMMAL, ATTRACTION_SCORE);
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    /**
     * The leopard prefers medium-sized prey with a body mass ranging from 0 to 40 kg.
     */
    @Override
    public boolean hunts(Animal other) {
        return other.getWeight() <= 40000 && !(other instanceof Leopard);
    }

    /**
     * Leopards have a very wide habitat tolerance.
     */
    @Override
    public boolean canLiveIn(Climate climate) {
        return climate != Climate.POLAR;
    }

    /**
     * @return the cost of a leopard.
     */
    @Override
    public int cost() {
        return COST;
    }

}
