package Zoo;

import Zoo.Animal.Animal;
import Zoo.Habitat.Climate;
import Zoo.Habitat.Habitat;

import java.util.*;

/**
 * This class is a singleton class that exposes the zoo's functionality.
 */
public class Zoo {
    /**
     * The number of credits the zoo has. Credits can be used to buy habitats or animals.
     */
    private int balance;

    /**
     * The amount of days that have passed since the creation of the zoo.
     */
    private int currentDay;

    /**
     * The list of used habitats.
     */
    private final ArrayList<Habitat> usedHabitats;

    /**
     * The list of unused habitats.
     */
    private final ArrayList<Habitat> unusedHabitats;


    /**
     * The list of unused animals. These are animals that have not yet been placed in a habitat.
     */
    private final ArrayList<Animal> unusedAnimals;

    /**
     * The singleton instance.
     */
    private static Zoo zoo = null;

    private Zoo() {
        this.balance = 100;
        this.currentDay = 0;
        this.unusedHabitats = new ArrayList<>();
        this.usedHabitats = new ArrayList<>();
        this.unusedAnimals = new ArrayList<>();
    }

    /**
     * Returns the instance of the zoo.
     */
    public static Zoo getInstance() {
        if (zoo == null) {
            zoo = new Zoo();
        }
        return zoo;
    }

    /**
     * @return the current balance.
     */
    public int getBalance() {
        return balance;
    }

    /**
     * @return the number of days since the creation of the zoo.
     */
    public int getCurrentDay() {
        return currentDay;
    }

    /**
     * @return an unmodifiable list of the used habitats.
     */
    public List<Habitat> getUsedHabitats() {
        return Collections.unmodifiableList(usedHabitats);
    }

    /**
     * @return an unmodifiable list of the unused habitats.
     */
    public List<Habitat> getUnusedHabitats() {
        return Collections.unmodifiableList(unusedHabitats);
    }

    /**
     * @return an unmodifiable list of unused animals.
     */
    public List<Animal> getUnusedAnimals() {
        return Collections.unmodifiableList(unusedAnimals);
    }

    /**
     * Removes an animal from a habitat and places it in the list of unused animals.
     *
     * @param animal  the animal to remove.
     * @param habitat the habitat the animal currently lives in.
     */
    public void removeAnimalFromHabitat(Animal animal, Habitat habitat) {
        for (Habitat h : usedHabitats) {
            if (h == habitat) {
                h.removeAnimal(animal);
                unusedAnimals.add(animal);
                break;
            }
        }
    }

    /**
     * Removes the habitat from the used habitat list and places it in the list of unused habitats.
     * All the animals inside this habitat will be placed in the unused animal list.
     *
     * @param habitat the habitat to remove.
     */
    public void removeHabitat(Habitat habitat) {
        for (Habitat h : usedHabitats) {
            if (h == habitat) {
                for (Animal animal : h.getAnimals()) {
                    h.removeAnimal(animal);
                    unusedAnimals.add(animal);
                }
                usedHabitats.remove(h);
                unusedHabitats.add(h);
                break;
            }
        }
    }

    /**
     * Removes the habitat from the unused habitat list and adds it to the used habitat list.
     *
     * @param habitat the habitat to add.
     */
    public void addHabitat(Habitat habitat) {
        for (Habitat h : unusedHabitats) {
            if (h == habitat) {
                usedHabitats.add(h);
                unusedHabitats.remove(h);
                break;
            }
        }
    }

    /**
     * Adds a new habitat to the list of unused habitats.
     *
     * @param habitat the habitat to add.
     */
    public void createHabitat(Habitat habitat) {
        unusedHabitats.add(habitat);
    }

    /**
     * Adds a new animal to the list of unused animals.
     *
     * @param animal the animal to add.
     */
    public void addAnimal(Animal animal) {
        unusedAnimals.add(animal);
    }
}
