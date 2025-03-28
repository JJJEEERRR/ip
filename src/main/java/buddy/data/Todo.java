package buddy.data;

/**
 * Represents a simple To-Do task in the Buddy application.
 * A Todo task only has a description without any date or time constraints.
 * It extends the base Task class and adds specific formatting and behavior.
 */
public class Todo extends Task {
    /**
     * Creates a new Todo task with the specified description.
     *
     * @param description The description of the Todo task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of this Todo task.
     * The string includes a [T] prefix, the completion status, and the description.
     *
     * @return A formatted string representing this Todo task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}