package buddy.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task in the Buddy application.
 * An Event has a description, a start date/time, and an end date/time.
 * It extends the base Task class and adds event-specific functionality.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
    private static final DateTimeFormatter STORAGE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    /**
     * Creates a new Event with the specified description and date/time strings.
     *
     * @param description The description of the event.
     * @param from The start date and time as a string.
     * @param to The end date and time as a string.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = parseDateTime(from);
        this.to = parseDateTime(to);
    }

    /**
     * Creates a new Event with the specified description and LocalDateTime objects.
     *
     * @param description The description of the event.
     * @param from The start date and time.
     * @param to The end date and time.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
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
     * Gets the start date and time of this event.
     *
     * @return The start date and time.
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Gets the end date and time of this event.
     *
     * @return The end date and time.
     */
    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Gets the start date and time formatted for storage.
     *
     * @return The start date and time formatted as a string for storage.
     */
    public String getFromForStorage() {
        return from.format(STORAGE_FORMATTER);
    }

    /**
     * Gets the end date and time formatted for storage.
     *
     * @return The end date and time formatted as a string for storage.
     */
    public String getToForStorage() {
        return to.format(STORAGE_FORMATTER);
    }

    /**
     * Checks if this event occurs on the given date.
     *
     * @param date The date to check against.
     * @return true if the event is on the same date, false otherwise.
     */
    public boolean isOnDate(LocalDateTime date) {
        return from.toLocalDate().equals(date.toLocalDate()) ||
                to.toLocalDate().equals(date.toLocalDate()) ||
                (date.toLocalDate().isAfter(from.toLocalDate()) && 
                 date.toLocalDate().isBefore(to.toLocalDate()));
    }

    /**
     * Returns a string representation of this Event.
     * The string includes an [E] prefix, the completion status, description, and event period.
     *
     * @return A formatted string representing this Event.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(DISPLAY_FORMATTER) +
                " to: " + to.format(DISPLAY_FORMATTER) + ")";
    }
}