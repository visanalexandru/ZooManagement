package zoo;

import zoo.animal.Animal;
import zoo.db.Database;
import zoo.db.MissingDataException;
import zoo.habitat.Climate;
import zoo.habitat.Habitat;
import zoo.habitat.InvalidHabitatException;
import zoo.shop.BalanceTooLowException;
import zoo.shop.Shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    /**
     * @throws SQLException         if there were any database errors.
     * @throws MissingDataException if any attribute is missing in the database.
     */
    private void loadAttributesFromDb() throws SQLException, MissingDataException {
        Database db = Database.getDatabase();
        Connection conn = db.getConnection();

        // Load all the zoo attributes from the database.
        PreparedStatement s = conn.prepareStatement("SELECT * FROM ZOO_ATTRIBUTES");
        ResultSet set = s.executeQuery();

        HashMap<String, Integer> values = new HashMap<>();
        while (set.next()) {
            String attribute = set.getString("name");
            String value = set.getString("value");
            values.put(attribute, Integer.parseInt(value));
        }

        // Now set the attributes or throw an exception if attributes are missing.
        if (values.containsKey("balance")) {
            this.balance = values.get("balance");
        } else {
            throw new MissingDataException("The balance is missing");
        }

        if (values.containsKey("currentDay")) {
            this.currentDay = values.get("currentDay");
        } else {
            throw new MissingDataException("The current day is missing");
        }
    }

    /**
     * Loads the zoo data from the database.
     */
    private void loadFromDb() throws SQLException, MissingDataException {
        loadAttributesFromDb();
    }

    private Zoo() {
        this.unusedHabitats = new ArrayList<>();
        this.usedHabitats = new ArrayList<>();
        this.unusedAnimals = new ArrayList<>();

        try {
            loadFromDb();
        } catch (Exception exception) {
            System.out.println("Could not load data from the database, reverting to default values: " + exception.getMessage());
            this.balance = 100;
            this.currentDay = 1;
            this.unusedHabitats.add(new Habitat("Temperate Climate Habitat", Climate.TEMPERATE));
        }
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
    public void addHabitatToUnused(Habitat habitat) {
        unusedHabitats.add(habitat);
    }

    /**
     * Adds a new animal to the list of unused animals.
     *
     * @param animal the animal to add.
     */
    public void addAnimalToUnused(Animal animal) {
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
     * Updates the attributes in the database accordingly.
     *
     * @throws SQLException if there were any database errors.
     */
    public void updateDbAttributes() throws SQLException {
        Database database = Database.getDatabase();
        Connection conn = database.getConnection();
        PreparedStatement statement = conn.prepareStatement("UPDATE ZOO_ATTRIBUTES SET value = ? WHERE name = ?");

        statement.setString(2, "currentDay");
        statement.setString(1, String.valueOf(currentDay));
        statement.executeUpdate();

        statement.setString(2, "balance");
        statement.setString(1, String.valueOf(balance));
        statement.executeUpdate();
    }


    /**
     * Increments the day counter and updates the balance based on how many visitors come to
     * the zoo. Each seven days, refill the shop.
     */
    public void nextDay() {
        int oldDay = currentDay;
        int oldBalance = balance;

        currentDay++;
        System.out.println("Got " + numVisitors() + " visitors last day.");
        balance += numVisitors() * 3;
        if (currentDay % 7 == 0) {
            Shop.getInstance().refill();
        }

        // Try update the database.
        try {
            updateDbAttributes();
        } catch (SQLException exception) {
            // Database error, revert the attributes back to their old values.
            System.out.println("Could not update the database: " + exception.getMessage());
            currentDay = oldDay;
            balance = oldBalance;
        }
    }

    public void purchaseProduct(Purchesable product) throws BalanceTooLowException {
        if (product.cost() > balance) {
            throw new BalanceTooLowException("Balance too low.");
        }
        balance -= product.cost();

        // Try update the database.
        try {
            updateDbAttributes();
        } catch (SQLException exception) {
            // Database error, revert the attributes back to their old values.
            System.out.println("Could not update the database: " + exception.getMessage());
            balance += product.cost();
            return;
        }

        Shop.getInstance().removeProduct(product);

        if (product instanceof Animal) {
            addAnimalToUnused((Animal) product);
        } else if (product instanceof Habitat) {
            addHabitatToUnused((Habitat) product);
        }
    }
}
