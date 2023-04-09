package Zoo.Animal;

import Zoo.Habitat.Climate;

public class WildBoar extends Animal {
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
