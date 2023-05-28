package zoo.habitat;

import zoo.animal.Animal;
import zoo.Purchesable;
import zoo.db.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * This class is the base class for all the habitats in the zoo.
 */
public class Habitat implements Purchesable {
    /**
     * The id of the habitat.
     */
    private final String id;

    /**
     * The name of the habitat.
     */
    private String name;

    /**
     * True if the habitat is used, else false.
     */
    private boolean used;

    /**
     * The animals residing in this habitat.
     */
    private final ArrayList<Animal> animals;

    /**
     * The climate of the habitat.
     */
    private final Climate climate;

    /**
     * Loads the habitat with the given id from the database.
     *
     * @param id the id of the habitat to load.
     * @return the habitat with the given id.
     * @throws SQLException if there were any database errors.
     */
    public static Habitat loadHabitatFromDb(String id) throws SQLException {
        Database database = Database.getDatabase();
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT name,climate,used FROM HABITAT WHERE id = ?");
        stmt.setString(1, id);
        ResultSet set = stmt.executeQuery();
        set.next();
        return new Habitat(id, set.getString(1), Climate.valueOf(set.getString(2)), set.getBoolean(3), new ArrayList<>());
    }

    /**
     * This constructor is used to easily load habitats from the database.
     */
    private Habitat(String id, String name, Climate climate, boolean used, ArrayList<Animal> animals) {
        this.id = id;
        this.name = name;
        this.climate = climate;
        this.used = used;
        this.animals = animals;
    }

    public Habitat(String name, Climate climate) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.climate = climate;
        this.used = false;
        animals = new ArrayList<>();
    }

    /**
     * @return the name of the habitat.
     */
    public String getName() {
        return name;
    }

    /**
     * Set a new name to the habitat and update the database to reflect the change.
     *
     * @param name the new name of the habitat.
     */
    public void setName(String name) {
        Database database = Database.getDatabase();
        Connection connection = database.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE HABITAT SET name= ? WHERE id = ?");
            stmt.setString(1, name);
            stmt.setString(2, id);
            stmt.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("Could not update the name in the database: " + exception.getMessage());
        }
        this.name = name;
    }

    /**
     * @return the id of the habitat.
     */
    public String getId() {
        return id;
    }

    /**
     * @return the climate of the habitat.
     */
    public Climate getClimate() {
        return climate;
    }

    /**
     * @return true if the habitat is used, else false.
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * Updates the used variable and the database to reflect the change.
     *
     * @param used if the habitat is used or not.
     */
    public void setUsed(boolean used) {
        Database database = Database.getDatabase();
        Connection connection = database.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE HABITAT SET used = ? WHERE id = ?");
            stmt.setBoolean(1, used);
            stmt.setString(2, id);
            stmt.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("Could not update the used flag in the database: " + exception.getMessage());
        }
        this.used = used;
    }

    /**
     * @return the list of animals.
     */
    public List<Animal> getAnimals() {
        return animals;
    }

    /**
     * Adds the new animal in this habitat and updates the database accordingly.
     *
     * @param animal the new animal to add to the habitat.
     * @throws InvalidHabitatException if the given animal is not compatible with this habitat.
     */
    public void addAnimal(Animal animal) throws InvalidHabitatException {
        if (!animal.canLiveIn(climate))
            throw new InvalidHabitatException(animal.getName() + " cannot live in " + climate.toString());
        for (Animal toCheck : animals) {
            if (!animal.canCoexist(toCheck))
                throw new InvalidHabitatException(animal.getName() + " cannot coexist with " + toCheck.getName());
        }

        Database database = Database.getDatabase();
        Connection connection = database.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO ANIMAL_IN_HABITAT VALUES(?,?)");
            stmt.setString(1, animal.getId());
            stmt.setString(2, this.id);
            stmt.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("Could not insert the new association in the database: " + exception.getMessage());
        }
        animals.add(animal);
    }

    /**
     * Removes the animal from this habitat and updates the database accordingly.
     *
     * @param animal the animal to remove from the habitat.
     */
    public void removeAnimal(Animal animal) {
        Database database = Database.getDatabase();
        Connection connection = database.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM ANIMAL_IN_HABITAT WHERE animal_id = ? AND habitat_id = ?");
            stmt.setString(1, animal.getId());
            stmt.setString(2, this.id);
            stmt.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("Could not delete the association from the database: " + exception.getMessage());
        }
        animals.remove(animal);
    }

    /**
     * Compute the sum of the attraction scores of the animals in the habitat.
     *
     * @return the sum of the scores of the animals.
     */
    public int getAttractionScore() {
        int total = 0;
        for (Animal animal : animals) {
            total += animal.getAttractionScore();
        }
        return total;
    }

    /**
     * The cost of the habitat is based on the climate of the habitat.
     *
     * @return the cost of the habitat.
     */
    @Override
    public int cost() {
        if (climate == Climate.TROPICAL) {
            return 300;
        } else if (climate == Climate.DRY) {
            return 200;
        } else if (climate == Climate.TEMPERATE) {
            return 150;
        } else if (climate == Climate.CONTINENTAL) {
            return 300;
        } else if (climate == Climate.POLAR) {
            return 600;
        }
        return 0;
    }

    public void saveToDb() throws SQLException {
        Database database = Database.getDatabase();
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO HABITAT (id, name, climate, used) VALUES (?,?,?,?)");
        stmt.setString(1, id);
        stmt.setString(2, name);
        stmt.setString(3, climate.toString());
        stmt.setBoolean(4, used);
        stmt.executeUpdate();
    }
}
