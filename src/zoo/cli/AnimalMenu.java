package zoo.cli;

import zoo.animal.Animal;
import zoo.habitat.Habitat;
import zoo.habitat.InvalidHabitatException;
import zoo.shop.BalanceTooLowException;
import zoo.Zoo;

public class AnimalMenu extends Menu {

    /**
     * The animal the menu refers to.
     */
    private final Animal animal;

    /**
     * The habitat in which the animal the menu refers to resides.
     */
    private final Habitat habitat;

    /**
     * The context in which this menu is used.
     */
    private final Context context;

    /**
     * @param animal  the animal.
     * @param habitat the habitat of the animal.
     * @param context the context of the menu.
     */
    public AnimalMenu(Animal animal, Habitat habitat, Context context) {
        this.animal = animal;
        this.habitat = habitat;
        this.context = context;
    }

    /**
     * Render the animal menu.
     */
    @Override
    public void render() {
        while (true) {
            System.out.println(animal.getName());
            System.out.println(animal.getDescription());
            System.out.println("Age: " + animal.getAge());
            System.out.println("Weight: " + animal.getWeight());
            System.out.println("Size: " + animal.getSize());
            System.out.println("Id: " + animal.getId());

            if (context == Context.ANIMAL_UNUSED) {
                System.out.println("1) Add to this habitat");
                System.out.println("2) Exit");

            } else if (context == Context.ANIMAL_USED) {
                System.out.println("1) Remove from this habitat");
                System.out.println("2) Exit");
            } else if (context == Context.ANIMAL_STORE) {
                System.out.println("1) Buy this animal, cost: " + animal.cost());
                System.out.println("2) Exit");
            }

            int choice = readInt(1, 2);
            if (choice == 2) {
                break;
            }

            if (context == Context.ANIMAL_UNUSED) {
                try {
                    Zoo.getInstance().addAnimalToHabitat(animal, habitat);
                    break;
                } catch (InvalidHabitatException e) {
                    System.out.println("Cannot add animal to this habitat: " + e.getMessage());
                }

            } else if (context == Context.ANIMAL_USED) {
                Zoo.getInstance().removeAnimalFromHabitat(animal, habitat);
                break;
            } else if (context == Context.ANIMAL_STORE) {
                try {
                    Zoo.getInstance().purchaseProduct(animal);
                    break;
                } catch (BalanceTooLowException e) {
                    System.out.println("Cannot buy this animal: " + e.getMessage());
                }
            }
        }


    }

}
