import Zoo.Animal.Animal;
import Zoo.Cli.MainMenu;
import Zoo.Habitat.InvalidHabitatException;
import Zoo.Habitat.Habitat;
import Zoo.Animal.Leopard;
import Zoo.Animal.RedPanda;
import Zoo.Habitat.Climate;
import Zoo.Zoo;

public class Main {
    public static void main(String[] args) throws InvalidHabitatException {
        Habitat h = new Habitat("test", Climate.TEMPERATE);

        Zoo.getInstance().createHabitat(h);
        Zoo.getInstance().addAnimal(new RedPanda(1, 6803, 4));

        MainMenu menu = new MainMenu();
        menu.render();
    }
}