import java.time.LocalDateTime;

public class Command {
    private CommandType type;
    private String description;
    private int taskIndex;
    private LocalDateTime byDate;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private LocalDateTime searchDate;

    // Constructor for commands like EXIT, LIST
    public Command(CommandType type) {
        this.type = type;
    }

    // Constructor for commands like MARK, UNMARK, DELETE
    public Command(CommandType type, int taskIndex) {
        this.type = type;
        this.taskIndex = taskIndex;
    }

    // Constructor for commands like TODO, FIND
    public Command(CommandType type, String description) {
        this.type = type;
        this.description = description;
    }

    // Constructor for commands like DATE
    public Command(CommandType type, LocalDateTime searchDate) {
        this.type = type;
        this.searchDate = searchDate;
    }

    // Constructor for DEADLINE command
    public Command(CommandType type, String description, LocalDateTime byDate) {
        this.type = type;
        this.description = description;
        this.byDate = byDate;
    }

    // Constructor for EVENT command
    public Command(CommandType type, String description, LocalDateTime fromDate, LocalDateTime toDate) {
        this.type = type;
        this.description = description;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public CommandType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getTaskIndex() {
        return taskIndex;
    }

    public LocalDateTime getByDate() {
        return byDate;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public LocalDateTime getSearchDate() {
        return searchDate;
    }

    // Determine if this command should trigger a save operation
    public boolean shouldSave() {
        return type == CommandType.TODO ||
                type == CommandType.DEADLINE ||
                type == CommandType.EVENT ||
                type == CommandType.MARK ||
                type == CommandType.UNMARK ||
                type == CommandType.DELETE;
    }
}