package zoo.db;

/**
 * This exception is thrown whenever the database is missing data that needs to be
 * used.
 */
public class MissingDataException extends Exception {
    public MissingDataException(String message) {
        super(message);
    }
}
