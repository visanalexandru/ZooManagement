package zoo.shop;

/**
 * This exception is thrown when the balance is too low to accomodate
 * the purchase of a product.
 */
public class BalanceTooLowException extends Exception {
    public BalanceTooLowException(String message) {
        super(message);
    }
}

