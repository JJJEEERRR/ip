package buddy.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import buddy.util.BuddyException;

/**
 * Represents a list of tasks in the Buddy application.
 * Provides methods to add, retrieve, modify, and delete tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list with the given tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Gets all tasks in this task list.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Gets the number of tasks in this task list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Retrieves a task at the specified index.
     *
     * @param index The zero-based index of the task.
     * @return The task at the specified index.
     * @throws BuddyException If the index is out of range.
     */
    public Task getTask(int index) throws BuddyException {
        if (index < 0 || index >= tasks.size()) {
            throw new BuddyException("buddy.data.Task index out of range: " + (index + 1));
        }
        return tasks.get(index);
    }

    /**
     * Adds a new todo task to the list.
     *
     * @param description The description of the todo task.
     * @return The newly created task.
     */
    public Task addTodo(String description) {
        Task task = new Todo(description);
        tasks.add(task);
        return task;
    }

    /**
     * Adds a new deadline task to the list.
     *
     * @param description The description of the deadline task.
     * @param by The date and time by which the task should be completed.
     * @return The newly created task.
     */
    public Task addDeadline(String description, LocalDateTime by) {
        Task task = new Deadline(description, by);
        tasks.add(task);
        return task;
    }

    /**
     * Adds a new event task to the list.
     *
     * @param description The description of the event.
     * @param from The start date and time of the event.
     * @param to The end date and time of the event.
     * @return The newly created task.
     */
    public Task addEvent(String description, LocalDateTime from, LocalDateTime to) {
        Task task = new Event(description, from, to);
        tasks.add(task);
        return task;
    }

    /**
     * Marks a task as done.
     *
     * @param index The zero-based index of the task to mark as done.
     * @return The updated task.
     * @throws BuddyException If the index is out of range.
     */
    public Task markTaskAsDone(int index) throws BuddyException {
        Task task = getTask(index);
        task.markAsDone();
        return task;
    }

    /**
     * Marks a task as not done.
     *
     * @param index The zero-based index of the task to mark as not done.
     * @return The updated task.
     * @throws BuddyException If the index is out of range.
     */
    public Task markTaskAsUndone(int index) throws BuddyException {
        Task task = getTask(index);
        task.markAsUndone();
        return task;
    }

    /**
     * Deletes a task from the list.
     *
     * @param index The zero-based index of the task to delete.
     * @return The deleted task.
     * @throws BuddyException If the index is out of range.
     */
    public Task deleteTask(int index) throws BuddyException {
        if (index < 0 || index >= tasks.size()) {
            throw new BuddyException("buddy.data.Task index out of range: " + (index + 1));
        }
        return tasks.remove(index);
    }

    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }

    public ArrayList<Task> getTasksOnDate(LocalDateTime date) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (deadline.isOnDate(date)) {
                    matchingTasks.add(task);
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                if (event.isOnDate(date)) {
                    matchingTasks.add(task);
                }
            }
        }
        return matchingTasks;
    }
}