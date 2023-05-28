package zoo.cli;

import zoo.Zoo;

/**
 * The main menu in the program.
 */
public class MainMenu extends Menu {
    /**
     * Render the main menu.
     */
    @Override
    public void render() {

        while (true) {
            System.out.println("Day: " + Zoo.getInstance().getCurrentDay());
            System.out.println("Main menu");
            System.out.println("1) Habitats menu");
            System.out.println("2) Shop menu");
            System.out.println("3) Show stats");
            System.out.println("4) Next Day");
            System.out.println("5) Exit");
            int option = readInt(1, 5);
            if (option == 1) {
                new HabitatsMenu().render();
            } else if (option == 2) {
                new ShopMenu().render();
            } else if (option == 3) {
                System.out.println();
                System.out.println("Number of animals: " + Zoo.getInstance().numAnimals());
                System.out.println("Number of habitats: " + Zoo.getInstance().numHabitats());
                System.out.println("Attraction score: " + Zoo.getInstance().attractionScore());
                System.out.println();
            } else if (option == 4) {
                Zoo.getInstance().nextDay();
            } else {
                break;
            }

        }

    }
}
