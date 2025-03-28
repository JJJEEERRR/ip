package buddy.data;

/**
 * Represents a general task in the Buddy application.
 * This is the base class for all task types, providing common functionality
 * such as description management and completion status tracking.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new task with the specified description.
     * All new tasks are initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Gets the status icon to represent the completion status of this task.
     * 
     * @return "[X]" if the task is done, "[ ]" otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]");
    }

    /**
     * Gets the description of this task.
     * 
     * @return The task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks whether this task is done.
     * 
     * @return true if the task is done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns a string representation of this task.
     * 
     * @return A formatted string showing the task's status and description.
     */
    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }
}