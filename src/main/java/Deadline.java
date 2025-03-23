import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
    private static final DateTimeFormatter STORAGE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public Deadline(String description, String by) {
        super(description);
        this.by = parseDateTime(by);
    }

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
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

    public LocalDateTime getBy() {
        return by;
    }

    public String getByForStorage() {
        return by.format(STORAGE_FORMATTER);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DISPLAY_FORMATTER) + ")";
    }

    /**
     * Checks if this deadline occurs on the given date.
     */
    public boolean isOnDate(LocalDateTime date) {
        return by.toLocalDate().equals(date.toLocalDate());
    }
}