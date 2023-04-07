package Zoo.Habitat;

/**
 * This exception is thrown whenever an animal is placed in an incompatible habitat.
 */
public class InvalidHabitatException extends Exception {
    public InvalidHabitatException(String message) {
        super(message);
    }
}
