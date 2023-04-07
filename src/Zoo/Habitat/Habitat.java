package Zoo.Habitat;

import Zoo.Animal.Animal;
import Zoo.Animal.AnimalType;

import java.util.ArrayList;

/**
 * This class is the base class for all the habitats in the zoo.
 */
public class Habitat {
    /**
     * The name of the habitat.
     */
    private String name;

    /**
     * The animals residing in this habitat.
     */
    private ArrayList<Animal> animals;

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
     * @param animal the new animal to add to the habitat.
     * @throws InvalidHabitatException when the given animal is not compatible with this habitat.
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
}
