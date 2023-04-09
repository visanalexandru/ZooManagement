package Zoo;

import Zoo.Animal.Animal;
import Zoo.Habitat.Habitat;

import java.lang.reflect.Array;
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
     * A map from the name of the habitats to the habitats.
     */
    private final ArrayList<Habitat> habitats;

    /**
     * The list of unused animals. These are animals that have not yet been placed in a habitat.
     */
    private final ArrayList<Animal> unused;

    /**
     * The singleton instance.
     */
    private static Zoo zoo = null;

    private Zoo() {
        this.balance = 100;
        this.habitats = new ArrayList<>();
        this.unused = new ArrayList<>();
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
     * @return an unmodifiable list of habitats.
     */
    public List<Habitat> getHabitats() {
        return Collections.unmodifiableList(habitats);
    }

    /**
     * @return an unmodifiable list of unused animals.
     */
    public List<Animal> getUnused() {
        return Collections.unmodifiableList(unused);
    }
}
