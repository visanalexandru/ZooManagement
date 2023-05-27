package zoo.habitat;

import zoo.animal.Animal;
import zoo.Purchesable;

import java.util.*;

/**
 * This class is the base class for all the habitats in the zoo.
 */
public class Habitat implements Purchesable {
    /**
     * The name of the habitat.
     */
    private String name;

    /**
     * The animals residing in this habitat.
     */
    private final ArrayList<Animal> animals;

    /**
     * The climate of the habitat.
     */
    private final Climate climate;

    public Habitat(String name, Climate climate) {
        this.name = name;
        this.climate = climate;
        animals = new ArrayList<>();
    }

    /**
     * @return the name of the habitat.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the new name of the habitat.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the climate of the habitat.
     */
    public Climate getClimate() {
        return climate;
    }

    /**
     * @return the list of animals.
     */
    public List<Animal> getAnimals() {
        return animals;
    }

    /**
     * @param animal the new animal to add to the habitat.
     * @throws InvalidHabitatException if the given animal is not compatible with this habitat.
     */
    public void addAnimal(Animal animal) throws InvalidHabitatException {
        if (!animal.canLiveIn(climate))
            throw new InvalidHabitatException(animal.getName() + " cannot live in " + climate.toString());
        for (Animal toCheck : animals) {
            if (!animal.canCoexist(toCheck))
                throw new InvalidHabitatException(animal.getName() + " cannot coexist with " + toCheck.getName());
        }
        animals.add(animal);
    }

    /**
     * @param animal the animal to remove from the habitat.
     */
    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    /**
     * Compute the sum of the attraction scores of the animals in the habitat.
     *
     * @return the sum of the scores of the animals.
     */
    public int getAttractionScore() {
        int total = 0;
        for (Animal animal : animals) {
            total += animal.getAttractionScore();
        }
        return total;
    }

    /**
     * The cost of the habitat is based on the climate of the habitat.
     *
     * @return the cost of the habitat.
     */
    @Override
    public int cost() {
        if (climate == Climate.TROPICAL) {
            return 300;
        } else if (climate == Climate.DRY) {
            return 200;
        } else if (climate == Climate.TEMPERATE) {
            return 150;
        } else if (climate == Climate.CONTINENTAL) {
            return 300;
        } else if (climate == Climate.POLAR) {
            return 600;
        }
        return 0;
    }
}