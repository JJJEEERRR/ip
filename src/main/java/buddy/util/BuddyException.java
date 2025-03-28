package buddy.util;

/**
 * Custom exception for the Buddy application.
 * Used to represent various error conditions specific to the application.
 */
public class BuddyException extends Exception {
    /**
     * Creates a new exception with the specified error message.
     *
     * @param message The error message describing the exception.
     */
    public BuddyException(String message) {
        super(message);
    }
}