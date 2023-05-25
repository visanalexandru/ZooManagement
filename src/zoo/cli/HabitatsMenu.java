package zoo.cli;

import zoo.Zoo;
import zoo.habitat.Habitat;

import java.util.List;

/**
 * The menu of the habitats. Renders the habitat list.
 */
public class HabitatsMenu extends Menu {

    /**
     * Render the list of given habitats, and enable the user to choose a habitat for inspection.
     *
     * @param habitats the list of habitats to show.
     * @param context  the context in which the list is shown.
     */
    private void showHabitatList(List<Habitat> habitats, Context context) {
        for (int i = 0; i < habitats.size(); i++) {
            System.out.println(i + 1 + ") " + habitats.get(i).getName());
        }
        System.out.println(habitats.size() + 1 + ") " + "Exit");
        int choice = readInt(1, habitats.size() + 1);
        if (choice != habitats.size() + 1) {
            new HabitatMenu(habitats.get(choice - 1), context).render();
        }
    }

    /**
     * Render the menu of the habitats.
     */
    @Override
    public void render() {
        while (true) {
            System.out.println("Habitats menu");
            System.out.println("1) Show used habitats");
            System.out.println("2) Show unused habitats");
            System.out.println("3) Exit");

            int option = readInt(1, 4);
            if (option == 1) {
                var habitats = Zoo.getInstance().getUsedHabitats();
                showHabitatList(habitats, Context.HABITAT_USED);
            } else if (option == 2) {
                var habitats = Zoo.getInstance().getUnusedHabitats();
                showHabitatList(habitats, Context.HABITAT_UNUSED);
            } else {
                break;
            }
        }

    }

}
