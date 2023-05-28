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
     * The list of habitats.
     */
    private final ArrayList<Habitat> habitats;


    /**
     * The list of animals.
     */
    private final ArrayList<Animal> animals;

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
        PreparedStatement s = conn.prepareStatement("SELECT * FROM ZOO_ATTRIBUTE");
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
     * Loads the habitat list from the database.
     *
     * @throws SQLException if there were any database errors.
     */
    private void loadHabitatsFromDb() throws SQLException {
        habitats.clear();
        Database database = Database.getDatabase();
        Connection conn = database.getConnection();

        // First query for all the habitat ids.
        PreparedStatement stmt = conn.prepareStatement("SELECT id FROM HABITAT");
        ResultSet set = stmt.executeQuery();

        while (set.next()) {
            String id = set.getString("id");
            habitats.add(Habitat.loadHabitatFromDb(id));
        }
    }

    /**
     * This method queries the ANIMAL_IN_HABITAT table so that we load information about
     * the placement of the animals.
     *
     * @throws SQLException if there were any database errors.
     */
    private void loadAssociationsFromDb() throws SQLException {
        Database database = Database.getDatabase();
        Connection conn = database.getConnection();

        PreparedStatement stmt = conn.prepareStatement("SELECT animal_id,habitat_id FROM ANIMAL_IN_HABITAT");
        ResultSet set = stmt.executeQuery();

        while (set.next()) {
            String animalId = set.getString(1);
            String habitatId = set.getString(2);

            // Find the animal with the given id.
            Animal toPlace = animals.stream().filter(animal -> animal.getId().equals(animalId)).
                    findFirst().orElseThrow(() -> new RuntimeException("The given animal id was not found in the list of animals!"));
            Habitat in = habitats.stream().filter(habitat -> habitat.getId().equals(habitatId)).
                    findFirst().orElseThrow(() -> new RuntimeException("The given habitat id was not found in the list of habitats!"));
            in.getAnimals().add(toPlace);
        }
    }

    /**
     * Loads the animal list from the database.
     *
     * @throws SQLException if there were any database errors.
     */
    private void loadAnimalsFromDb() throws SQLException {
        animals.clear();
        Database database = Database.getDatabase();
        Connection conn = database.getConnection();

        // First query for all the habitat ids.
        PreparedStatement stmt = conn.prepareStatement("SELECT id FROM ANIMAL");
        ResultSet set = stmt.executeQuery();

        while (set.next()) {
            String id = set.getString("id");
            animals.add(Animal.loadAnimalFromDb(id));
        }
    }

    /**
     * Loads the zoo data from the database.
     */
    private void loadFromDb() throws SQLException, MissingDataException {
        loadAttributesFromDb();
        loadHabitatsFromDb();
        loadAnimalsFromDb();
        loadAssociationsFromDb();
    }

    private Zoo() {
        this.habitats = new ArrayList<>();
        this.animals = new ArrayList<>();

        try {
            loadFromDb();
        } catch (Exception exception) {
            System.out.println("Could not load data from the database, reverting to default values: " + exception.getMessage());
            this.balance = 100;
            this.currentDay = 1;
            this.habitats.add(new Habitat("Temperate Climate Habitat", Climate.TEMPERATE));
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
     * Set the balance to the given balance and update the database.
     *
     * @param newBalance the new balance.
     */
    public void setBalance(int newBalance) {
        balance = newBalance;
        Database database = Database.getDatabase();
        Connection conn = database.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE ZOO_ATTRIBUTE SET value = ? WHERE name = ?");
            statement.setString(1, String.valueOf(newBalance));
            statement.setString(2, "balance");
            statement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("Could not update the database balance: " + exception.getMessage());
        }
    }

    /**
     * @return the number of days since the creation of the zoo.
     */
    public int getCurrentDay() {
        return currentDay;
    }

    /**
     * Set the current day to the new day and update the database.
     *
     * @param newCurrentDay the new day.
     */
    public void setCurrentDay(int newCurrentDay) {
        currentDay = newCurrentDay;
        Database database = Database.getDatabase();
        Connection conn = database.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE ZOO_ATTRIBUTE SET value = ? WHERE name = ?");
            statement.setString(1, String.valueOf(newCurrentDay));
            statement.setString(2, "currentDay");
            statement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("Could not update the database current day: " + exception.getMessage());
        }
    }

    /**
     * @return an unmodifiable list of the used habitats.
     */
    public List<Habitat> getUsedHabitats() {
        return habitats.stream().filter(Habitat::isUsed).toList();
    }

    /**
     * @return an unmodifiable list of the unused habitats.
     */
    public List<Habitat> getUnusedHabitats() {
        return habitats.stream().filter(x -> !x.isUsed()).toList();
    }

    /**
     * @return an unmodifiable list of unused animals.
     */
    public List<Animal> getUnusedAnimals() {
        return animals.stream().filter(x -> !x.isUsed()).toList();
    }

    /**
     * Removes an animal from a habitat.
     *
     * @param animal  the animal to remove.
     * @param habitat the habitat the animal currently lives in.
     */
    public void removeAnimalFromHabitat(Animal animal, Habitat habitat) {
        // Check if that habitat exists.
        if (!habitats.contains(habitat)) {
            return;
        }

        // It does not make sense to remove an animal from an unused habitat.
        if (!habitat.isUsed()) {
            return;
        }

        // Check if that animal exists in the animal list.
        if (!animals.contains(animal)) {
            return;
        }

        // Check if the animal is already in the animal list.
        if (!habitat.getAnimals().contains(animal)) {
            return;
        }

        Logger.getLogger().logMessage("Removed the animal: " + animal + " from the habitat: " + habitat + ".");
        // Remove it from the habitat and update the used status.
        habitat.removeAnimal(animal);
        animal.setUsed(false);
    }

    /**
     * Adds the given animal to the given habitat.
     *
     * @param animal  the unused animal.
     * @param habitat the habitat.
     */
    public void addAnimalToHabitat(Animal animal, Habitat habitat) throws InvalidHabitatException {
        // Check if that habitat exists.
        if (!habitats.contains(habitat)) {
            return;
        }

        // It does not make sense to add an animal to an unused habitat.
        if (!habitat.isUsed()) {
            return;
        }

        // Check if that animal exists in the animal list.
        if (!animals.contains(animal)) {
            return;
        }
        // Check if that habitat contains the given animal.
        if (habitat.getAnimals().contains(animal)) {
            return;
        }

        Logger.getLogger().logMessage("Added the animal: " + animal + " to the habitat: " + habitat + ".");
        habitat.addAnimal(animal);
        animal.setUsed(true);
    }


    /**
     * Removes the given habitat from the list of used habitats. All the animals contained
     * will be marked as unused.
     *
     * @param habitat the habitat to remove.
     */
    public void removeHabitat(Habitat habitat) {
        // Check if the habitat exists.
        if (!habitats.contains(habitat)) {
            return;
        }
        Logger.getLogger().logMessage("Now the habitat is unused: " + habitat + ".");

        List<Animal> animals = new ArrayList<>(habitat.getAnimals());

        for (Animal animal : animals) {
            habitat.removeAnimal(animal);
            animal.setUsed(false);
        }
        habitat.setUsed(false);
    }

    /**
     * Removes the habitat from the unused habitat list and adds it to the used habitat list.
     *
     * @param habitat the habitat to add.
     */
    public void addHabitat(Habitat habitat) {
        if (!habitats.contains(habitat)) {
            return;
        }
        Logger.getLogger().logMessage("Now using the habitat: " + habitat + ".");
        habitat.setUsed(true);
    }

    /**
     * Adds a new habitat to the list of unused habitats.
     *
     * @param habitat the habitat to add.
     */
    public void addNewHabitat(Habitat habitat) {
        Logger.getLogger().logMessage("Added a new habitat: " + habitat + ".");
        try {
            habitats.add(habitat);
            habitat.saveToDb();
        } catch (SQLException exception) {
            System.out.println("Cannot add a new habitat to the database: " + exception.getMessage());
        }
    }

    /**
     * Adds a new animal to the list of unused animals.
     *
     * @param animal the animal to add.
     */
    public void addNewAnimal(Animal animal) {
        Logger.getLogger().logMessage("Added a new animal: " + animal);
        try {
            animals.add(animal);
            animal.saveToDb();
        } catch (SQLException exception) {
            System.out.println("Cannot add a new animal to the database: " + exception.getMessage() + ".");
        }
    }

    /**
     * Computes the number of visitors that will come this day to the zoo based on the attraction
     * scores of the habitats.
     *
     * @return the number of visitors in this day.
     */
    private int numVisitors() {
        int totalScore = 0;
        for (Habitat h : habitats) {
            if (h.isUsed()) totalScore += h.getAttractionScore();
        }
        if (totalScore < 1) return 0;
        // Compute a random gaussian with the mean of log(totalScore) and deviation of 1.
        return (int) Rng.getRng().randomGaussian((float) Math.log(totalScore), 1);
    }


    /**
     * Increments the day counter and updates the balance based on how many visitors come to
     * the zoo. Each seven days, refill the shop.
     */
    public void nextDay() {
        Logger.getLogger().logMessage("Moved to the next day.");
        setCurrentDay(currentDay + 1);
        System.out.println("Got " + numVisitors() + " visitors last day.");
        setBalance(balance + numVisitors() * 3);
        if (currentDay % 7 == 0) {
            Shop.getInstance().refill();
        }
    }

    public void purchaseProduct(Purchesable product) throws BalanceTooLowException {
        if (product.cost() > balance) {
            throw new BalanceTooLowException("Balance too low.");
        }
        Logger.getLogger().logMessage("Purchased a new product: " + product + " for " + product.cost() + ".");
        setBalance(balance - product.cost());
        Shop.getInstance().removeProduct(product);

        if (product instanceof Animal) {
            addNewAnimal((Animal) product);
        } else if (product instanceof Habitat) {
            addNewHabitat((Habitat) product);
        }
    }
}
