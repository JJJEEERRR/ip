package buddy.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
    private static final DateTimeFormatter STORAGE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public Event(String description, String from, String to) {
        super(description);
        this.from = parseDateTime(from);
        this.to = parseDateTime(to);
    }

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

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

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public String getFromForStorage() {
        return from.format(STORAGE_FORMATTER);
    }

    public String getToForStorage() {
        return to.format(STORAGE_FORMATTER);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(DISPLAY_FORMATTER) +
                " to: " + to.format(DISPLAY_FORMATTER) + ")";
    }

    /**
     * Checks if this event occurs on the given date.
     */
    public boolean isOnDate(LocalDateTime date) {
        return from.toLocalDate().equals(date.toLocalDate()) ||
                to.toLocalDate().equals(date.toLocalDate()) ||
                (date.toLocalDate().isAfter(from.toLocalDate()) &&
                        date.toLocalDate().isBefore(to.toLocalDate()));
    }
}