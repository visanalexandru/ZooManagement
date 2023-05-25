package zoo.shop;

/**
 * This exception is thrown when the balance is too low to accommodate
 * the purchase of a product.
 */
public class BalanceTooLowException extends Exception {
    public BalanceTooLowException(String message) {
        super(message);
    }
}

