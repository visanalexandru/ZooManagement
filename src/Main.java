import Zoo.Animal.Leopard;
import Zoo.Animal.RedPanda;
import Zoo.Habitat.Climate;
import Zoo.Habitat.Habitat;
import Zoo.Habitat.InvalidHabitatException;

public class Main {
    public static void main(String[] args) throws InvalidHabitatException {
        Habitat h = new Habitat("test", Climate.TEMPERATE);
        h.addAnimal(new RedPanda(1, 6803, 4));
        h.addAnimal(new Leopard(1, 6803, 4));
    }
}