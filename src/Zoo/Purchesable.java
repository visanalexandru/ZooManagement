package Zoo;

/**
 * This interface is extended by classes that could be added in a shop.
 * They need to implement the cost() method.
 */
public interface Purchesable {
    /**
     * @return the cost of the object.
     */
    int cost();
}
