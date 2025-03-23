import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter DATE_ONLY_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy");

    public Command parseCommand(String input) throws BuddyException {
        String[] parts = input.split(" ", 2);
        String commandType = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1] : "";

        switch (commandType) {
            case "bye":
                return new Command(CommandType.EXIT);
            case "list":
                return new Command(CommandType.LIST);
            case "mark":
                if (arguments.trim().isEmpty()) {
                    throw new BuddyException("Please provide a task number to mark.");
                }
                try {
                    int taskIndex = Integer.parseInt(arguments.trim()) - 1;
                    return new Command(CommandType.MARK, taskIndex);
                } catch (NumberFormatException e) {
                    throw new BuddyException("Invalid task number format.");
                }
            case "unmark":
                if (arguments.trim().isEmpty()) {
                    throw new BuddyException("Please provide a task number to unmark.");
                }
                try {
                    int taskIndex = Integer.parseInt(arguments.trim()) - 1;
                    return new Command(CommandType.UNMARK, taskIndex);
                } catch (NumberFormatException e) {
                    throw new BuddyException("Invalid task number format.");
                }
            case "todo":
                if (arguments.trim().isEmpty()) {
                    throw new BuddyException("The description of a todo cannot be empty.");
                }
                return new Command(CommandType.TODO, arguments);
            case "deadline":
                return parseDeadlineCommand(arguments);
            case "event":
                return parseEventCommand(arguments);
            case "delete":
                if (arguments.trim().isEmpty()) {
                    throw new BuddyException("Please provide a task number to delete.");
                }
                try {
                    int taskIndex = Integer.parseInt(arguments.trim()) - 1;
                    return new Command(CommandType.DELETE, taskIndex);
                } catch (NumberFormatException e) {
                    throw new BuddyException("Invalid task number format.");
                }
            case "find":
                if (arguments.trim().isEmpty()) {
                    throw new BuddyException("Please provide a keyword to search for.");
                }
                return new Command(CommandType.FIND, arguments);
            case "date":
                if (arguments.trim().isEmpty()) {
                    throw new BuddyException("Please provide a date in d/M/yyyy format.");
                }
                try {
                    LocalDateTime searchDate = LocalDateTime.parse(arguments + " 0000", DATE_FORMATTER);
                    return new Command(CommandType.DATE, searchDate);
                } catch (DateTimeParseException e) {
                    throw new BuddyException("Invalid date format. Please use d/M/yyyy format (e.g., 2/12/2023)");
                }
            default:
                throw new BuddyException("Unknown command: " + commandType);
        }
    }

    private Command parseDeadlineCommand(String arguments) throws BuddyException {
        if (arguments.trim().isEmpty()) {
            throw new BuddyException("The description of a deadline cannot be empty.");
        }

        String[] parts = arguments.split(" /by ");
        if (parts.length != 2) {
            throw new BuddyException("Invalid deadline format. Please use: deadline <description> /by <date>\n" +
                    "Example: deadline return book /by 2/12/2023 1800");
        }

        String description = parts[0].trim();
        String byDateString = parts[1].trim();

        try {
            LocalDateTime byDate = LocalDateTime.parse(byDateString, DATE_FORMATTER);
            return new Command(CommandType.DEADLINE, description, byDate);
        } catch (DateTimeParseException e) {
            throw new BuddyException("Invalid date format. Please use: d/M/yyyy HHmm format (e.g., 2/12/2023 1800)");
        }
    }

    private Command parseEventCommand(String arguments) throws BuddyException {
        if (arguments.trim().isEmpty()) {
            throw new BuddyException("The description of an event cannot be empty.");
        }

        // Split by /from and /to
        String[] parts = arguments.split(" /from | /to ");
        if (parts.length != 3) {
            throw new BuddyException("Invalid event format. Please use: event <description> /from <start> /to <end>\n" +
                    "Example: event project meeting /from 6/8/2023 1400 /to 6/8/2023 1600");
        }

        String description = parts[0].trim();
        String fromDateString = parts[1].trim();
        String toDateString = parts[2].trim();

        try {
            LocalDateTime fromDate = LocalDateTime.parse(fromDateString, DATE_FORMATTER);
            LocalDateTime toDate = LocalDateTime.parse(toDateString, DATE_FORMATTER);
            return new Command(CommandType.EVENT, description, fromDate, toDate);
        } catch (DateTimeParseException e) {
            throw new BuddyException("Invalid date format. Please use: d/M/yyyy HHmm format (e.g., 6/8/2023 1400)");
        }
    }

    public LocalDateTime parseDateTime(String dateTimeString) throws BuddyException {
        try {
            return LocalDateTime.parse(dateTimeString, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            try {
                // Try as date-only format and set time to midnight
                return LocalDateTime.parse(dateTimeString + " 0000", DATE_FORMATTER);
            } catch (DateTimeParseException e2) {
                throw new BuddyException("Invalid date format: " + dateTimeString);
            }
        }
    }
}