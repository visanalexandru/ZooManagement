package zoo.cli;

import zoo.animal.Animal;
import zoo.habitat.Habitat;
import zoo.Purchesable;
import zoo.shop.Shop;
import zoo.Zoo;

import java.util.ArrayList;
import java.util.List;

/**
 * The shop menu is a menu where products can be bought.
 */
public class ShopMenu extends Menu {
    private void showHabitatList(List<Purchesable> products) {
        ArrayList<Habitat> habitats = new ArrayList<>();
        for (Purchesable p : products) {
            if (p instanceof Habitat) {
                habitats.add((Habitat) p);
            }
        }

        for (int i = 0; i < habitats.size(); i++) {
            System.out.println(i + 1 + ") " + habitats.get(i).getName());
        }
        System.out.println(habitats.size() + 1 + ") " + "Exit");
        int choice = readInt(1, habitats.size() + 1);
        if (choice != habitats.size() + 1) {
            new HabitatMenu(habitats.get(choice - 1), Context.HABITAT_STORE).render();
        }
    }

    private void showAnimalList(List<Purchesable> products) {
        ArrayList<Animal> animals = new ArrayList<>();
        for (Purchesable p : products) {
            if (p instanceof Animal) {
                animals.add((Animal) p);
            }
        }

        for (int i = 0; i < animals.size(); i++) {
            System.out.println(i + 1 + ") " + animals.get(i).getName());
        }
        System.out.println(animals.size() + 1 + ") " + "Exit");
        int choice = readInt(1, animals.size() + 1);
        if (choice != animals.size() + 1) {
            new AnimalMenu(animals.get(choice - 1), null, Context.ANIMAL_STORE).render();
        }
    }


    @Override
    public void render() {
        while (true) {
            System.out.println("Balance: " + Zoo.getInstance().getBalance());
            System.out.println("1) Show habitats to buy");
            System.out.println("2) Show animals to buy");
            System.out.println("3) Exit");
            int choice = readInt(1, 3);
            if (choice == 1) {
                showHabitatList(Shop.getInstance().getProducts());
            } else if (choice == 2) {
                showAnimalList(Shop.getInstance().getProducts());
            } else {
                break;
            }
        }
    }
}
