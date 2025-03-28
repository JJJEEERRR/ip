package buddy.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline in the Buddy application.
 * A Deadline task has a description and a due date/time by which it needs to be completed.
 * It extends the base Task class and adds deadline-specific functionality.
 */
public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
    private static final DateTimeFormatter STORAGE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    /**
     * Creates a new Deadline task with the specified description and deadline date.
     * The deadline date is provided as a string that will be parsed.
     *
     * @param description The description of the deadline task.
     * @param by The deadline date and time as a string.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = parseDateTime(by);
    }

    /**
     * Creates a new Deadline task with the specified description and deadline date.
     * The deadline date is provided directly as a LocalDateTime object.
     *
     * @param description The description of the deadline task.
     * @param by The deadline date and time.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Parses a date/time string into a LocalDateTime object.
     * Tries multiple formats for flexibility.
     *
     * @param dateTimeString The date/time string to parse.
     * @return The parsed LocalDateTime.
     */
    private LocalDateTime parseDateTime(String dateTimeString) {
        try {
            return LocalDateTime.parse(dateTimeString, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            try {
                // Try to parse as ISO format (for loading from file)
                return LocalDateTime.parse(dateTimeString, STORAGE_FORMATTER);
            } catch (DateTimeParseException e2) {
                System.out.println("Warning: Could not parse date '" + dateTimeString +
                        "'. Using current date and time instead.");
                return LocalDateTime.now();
            }
        }
    }

    /**
     * Gets the deadline date and time.
     *
     * @return The deadline as a LocalDateTime.
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Gets the deadline date and time formatted for storage.
     *
     * @return The deadline formatted as a string for storage.
     */
    public String getByForStorage() {
        return by.format(STORAGE_FORMATTER);
    }

    /**
     * Returns a string representation of this Deadline task.
     * The string includes a [D] prefix, the completion status, description, and deadline.
     *
     * @return A formatted string representing this Deadline task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DISPLAY_FORMATTER) + ")";
    }

    /**
     * Checks if this deadline occurs on the given date.
     *
     * @param date The date to check against.
     * @return true if the deadline is on the same date, false otherwise.
     */
    public boolean isOnDate(LocalDateTime date) {
        return by.toLocalDate().equals(date.toLocalDate());
    }
}