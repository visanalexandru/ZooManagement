package Zoo.Animal;

import Zoo.Habitat.Climate;

/**
 * This is the base class for all animal classes.
 **/
public abstract class Animal {
    /**
     * Used for giving each animal an id.
     **/
    static private int nextId = 1;

    /**
     * The id of the animal.
     **/
    private final int id;


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
    AnimalType type;

    public Animal(String name, int age, int weight, float size, AnimalType type) {
        this.id = nextId++;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.size = size;
        this.type = type;
    }

    /**
     * @return the id of the animal
     */
    public int getId() {
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
