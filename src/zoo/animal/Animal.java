package zoo.animal;

import zoo.db.Database;
import zoo.habitat.Climate;
import zoo.Purchesable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * This is the base class for all animal classes.
 **/
public abstract class Animal implements Purchesable {
    /**
     * The id of the animal.
     **/
    private final String id;

    /**
     * The name of the animal.
     **/
    private final String name;

    /**
     * The age of the animal, in days.
     **/
    private int age;

    /**
     * The weight of the animal, in grams.
     **/
    private int weight;

    /**
     * The size of the animal, in centimeters.
     **/
    private float size;

    /**
     * The type of the animal.
     **/
    final AnimalType type;


    /**
     * The attraction score of the animal is a number that is used to calculate the number
     * of visitors that come to a zoo in a day. If the attraction score is higher, more people
     * will visit the zoo.
     **/
    private int attractionScore;

    /**
     * True if the given animal is used (placed in a habitat) or else false.
     */
    private boolean used;

    /**
     * Loads the animal with the given id from the database.
     *
     * @param id the id of the animal.
     * @return the animal with the given id.
     * @throws SQLException if there were any database errors.
     */
    public static Animal loadAnimalFromDb(String id) throws SQLException {
        Database database = Database.getDatabase();
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT name,age,weight,size,used from ANIMAL WHERE id = ?");
        stmt.setString(1, id);
        ResultSet set = stmt.executeQuery();
        set.next();

        String name = set.getString(1);
        int age = set.getInt(2);
        int weight = set.getInt(3);
        float size = set.getFloat(4);
        boolean used = set.getBoolean(5);
        Animal toReturn;

        switch (name) {
            case "African Pygmy Goose" -> {
                toReturn = new AfricanPygmyGoose(id, age, weight, size, used);
            }
            case "Chinese Alligator" -> {
                toReturn = new ChineseAlligator(id, age, weight, size, used);
            }
            case "Leopard" -> {
                toReturn = new Leopard(id, age, weight, size, used);
            }
            case "Red Panda" -> {
                toReturn = new RedPanda(id, age, weight, size, used);
            }
            case "Wild Boar" -> {
                toReturn = new WildBoar(id, age, weight, size, used);
            }

            default -> {
                throw new RuntimeException("Unknown animal: " + name);
            }
        }
        return toReturn;
    }

    public void saveToDb() throws SQLException {
        Database database = Database.getDatabase();
        Connection conn = database.getConnection();

        PreparedStatement stmt = conn.prepareStatement("INSERT INTO ANIMAL(id, name, age, weight,size, used) VALUES (?,?,?,?,?,?)");

        stmt.setString(1, id);
        stmt.setString(2, name);
        stmt.setInt(3, age);
        stmt.setInt(4, weight);
        stmt.setFloat(5, size);
        stmt.setBoolean(6, used);

        stmt.executeUpdate();
    }

    /**
     * Used to easily load animals from the database.
     */
    protected Animal(String id, String name, int age, int weight, float size, AnimalType type, int attractionScore, boolean used) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.size = size;
        this.type = type;
        this.attractionScore = attractionScore;
        this.used = used;
    }

    protected Animal(String name, int age, int weight, float size, AnimalType type, int attractionScore) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.size = size;
        this.type = type;
        this.attractionScore = attractionScore;
        this.used = false;
    }

    /**
     * @return the id of the animal
     */
    public String getId() {
        return id;
    }

    /**
     * @return the name of the animal
     */
    public String getName() {
        return name;
    }

    /**
     * @return the age of the animal, in days.
     */
    public int getAge() {
        return age;
    }

    /**
     * @return the weight of the animal, in grams.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @return the size of the animal, in centimeters.
     */
    public float getSize() {
        return size;
    }

    /**
     * @param age the new age of the animal, in days.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @param weight the new weight of the animal, in grams.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * @param size the new size of the animal, in centimeters.
     */
    public void setSize(float size) {
        this.size = size;
    }

    /**
     * @return the type of the animal.
     */
    public AnimalType getType() {
        return type;
    }

    /**
     * @return the description of the animal
     */
    public abstract String getDescription();


    /**
     * @return the attraction score of the animal.
     */
    public int getAttractionScore() {
        return attractionScore;
    }

    /**
     * @param attractionScore the new attraction score of the animal.
     */
    public void setAttractionScore(int attractionScore) {
        this.attractionScore = attractionScore;
    }


    /**
     * @return true if the animal is used, else false.
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * Set the new used status and update the database to reflect the change.
     *
     * @param used if the animal is used or not.
     */
    public void setUsed(boolean used) {
        Database database = Database.getDatabase();
        Connection connection = database.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE ANIMAL SET used = ? WHERE id = ?");
            stmt.setBoolean(1, used);
            stmt.setString(2, id);
            stmt.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("Could not update the used flag in the database: " + exception.getMessage());
        }
        this.used = used;
    }

    /**
     * @param other the other animal.
     * @return true if this animal hunts the other animal, else false.
     */
    public abstract boolean hunts(Animal other);

    /**
     * @param other the other animal.
     * @return true if the two animals can coexist in the same habitat, else false.
     */
    public boolean canCoexist(Animal other) {
        // The two can coexist if neither animal can eat the other.
        return !this.hunts(other) && !other.hunts(this);
    }

    /**
     * @param climate the climate to check if this animal can live in.
     * @return true if this animal can live in the given climate, else false.
     */
    public abstract boolean canLiveIn(Climate climate);
}
