package zoo;

import zoo.animal.Animal;
import zoo.habitat.Climate;
import zoo.habitat.Habitat;
import zoo.habitat.InvalidHabitatException;
import zoo.shop.BalanceTooLowException;
import zoo.shop.Shop;

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
        this.balance = 1000;
        this.currentDay = 1;
        this.unusedHabitats = new ArrayList<>();
        this.usedHabitats = new ArrayList<>();
        this.unusedAnimals = new ArrayList<>();


        this.unusedHabitats.add(new Habitat("Temperate Climate Habitat", Climate.TEMPERATE));
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
     * Removes an animal from the list of unused animals and places it in
     * the given habitat.
     *
     * @param animal  the unused animal.
     * @param habitat the habitat.
     */
    public void addAnimalToHabitat(Animal animal, Habitat habitat) throws InvalidHabitatException {
        for (Animal a : unusedAnimals) {
            if (a == animal) {
                habitat.addAnimal(a);
                unusedAnimals.remove(a);
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
                Iterator<Animal> iter = h.getAnimals().iterator();
                while (iter.hasNext()) {
                    unusedAnimals.add(iter.next());
                    iter.remove();
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

    /**
     * Computes the number of visitors that will come this day to the zoo based on the attraction
     * scores of the habitats.
     *
     * @return the number of visitors in this day.
     */
    private int numVisitors() {
        int totalScore = 0;
        for (Habitat h : usedHabitats) {
            totalScore += h.getAttractionScore();
        }
        if (totalScore < 1)
            return 0;
        // Compute a random gaussian with the mean of log(totalScore) and deviation of 1.
        return (int) Rng.getRng().randomGaussian((float) Math.log(totalScore), 1);
    }


    /**
     * Increments the day counter and updates the balance based on how many visitors come to
     * the zoo. Each seven days, refill the shop.
     */
    public void nextDay() {
        currentDay++;
        System.out.println("Got " + numVisitors() + " visitors last day.");
        this.balance += numVisitors() * 3;
        if (currentDay % 7 == 0) {
            Shop.getInstance().refill();
        }
    }

    public void purchaseProduct(Purchesable product) throws BalanceTooLowException {
        if (product.cost() > balance) {
            throw new BalanceTooLowException("Balance too low.");
        }
        Shop.getInstance().removeProduct(product);
        balance -= product.cost();

        if (product instanceof Animal) {
            unusedAnimals.add((Animal) product);
        } else if (product instanceof Habitat) {
            unusedHabitats.add((Habitat) product);
        }
    }
}
