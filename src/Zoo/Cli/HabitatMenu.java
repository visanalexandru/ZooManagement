package Zoo.Cli;

import Zoo.Animal.Animal;
import Zoo.Habitat.Habitat;
import Zoo.Shop.BalanceTooLowException;
import Zoo.Zoo;

import java.util.List;

/**
 * All operations that can be applied on a habitat.
 */
public class HabitatMenu extends Menu {

    /**
     * The habitat the menu refers to.
     */
    private final Habitat habitat;

    /**
     * The context in which this menu is used.
     */
    private final Context context;

    public HabitatMenu(Habitat habitat, Context context) {
        this.habitat = habitat;
        this.context = context;
    }

    /**
     * Render the list of given animals and enable the user to choose an animal for inspection.
     *
     * @param animals the list of animals to show.
     * @param context the context of the list.
     */
    private void showAnimalList(List<Animal> animals, Context context) {
        for (int i = 0; i < animals.size(); i++) {
            System.out.println(i + 1 + ") " + animals.get(i).getName());
        }
        System.out.println(animals.size() + 1 + ") " + "Exit");
        int choice = readInt(1, animals.size() + 1);
        if (choice != animals.size() + 1) {
            new AnimalMenu(animals.get(choice - 1), habitat, context).render();
        }
    }

    /**
     * Render the habitat menu.
     */
    @Override
    public void render() {
        while (true) {

            System.out.println("Habitat " + habitat.getName());
            System.out.println("Climate: " + habitat.getClimate());


            if (context == Context.HABITAT_USED) {
                System.out.println("1) Show animals in this habitat");
                System.out.println("2) Add a new animal to this habitat");
                System.out.println("3) Rename the habitat");
                System.out.println("4) Remove the habitat from the zoo");
                System.out.println("5) Exit");

                int option = readInt(1, 5);
                if (option == 1) {
                    var animals = habitat.getAnimals();
                    showAnimalList(animals, Context.ANIMAL_USED);
                } else if (option == 2) {
                    var unusedAnimals = Zoo.getInstance().getUnusedAnimals();
                    showAnimalList(unusedAnimals, Context.ANIMAL_UNUSED);
                } else if (option == 3) {
                    System.out.println("New name:");
                    String name = readLine();
                    habitat.setName(name);
                } else if (option == 4) {
                    Zoo.getInstance().removeHabitat(habitat);
                    break;
                } else if (option == 5) {
                    break;
                }


            } else if (context == Context.HABITAT_UNUSED) {
                System.out.println("1) Rename the habitat");
                System.out.println("2) Add the habitat to the zoo");
                System.out.println("3) Exit");
                int option = readInt(1, 3);
                if (option == 1) {
                    System.out.println("New name:");
                    String name = readLine();
                    habitat.setName(name);
                } else if (option == 2) {
                    Zoo.getInstance().addHabitat(habitat);
                    break;
                } else {
                    break;
                }
            } else if (context == Context.HABITAT_STORE) {
                System.out.println("1) Buy this habitat");
                System.out.println("2) Exit");
                int option = readInt(1, 2);
                if (option == 1) {
                    try {
                        Zoo.getInstance().purchaseProduct(habitat);
                        break;
                    } catch (BalanceTooLowException e) {
                        System.out.println("Cannot buy this habitat: " + e.getMessage());
                    }
                } else if (option == 2) {
                    break;
                }
            }
        }
    }
}
