package buddy.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import buddy.util.BuddyException;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }

    public Task getTask(int index) throws BuddyException {
        if (index < 0 || index >= tasks.size()) {
            throw new BuddyException("buddy.data.Task index out of range: " + (index + 1));
        }
        return tasks.get(index);
    }

    public Task addTodo(String description) {
        Task task = new Todo(description);
        tasks.add(task);
        return task;
    }

    public Task addDeadline(String description, LocalDateTime by) {
        Task task = new Deadline(description, by);
        tasks.add(task);
        return task;
    }

    public Task addEvent(String description, LocalDateTime from, LocalDateTime to) {
        Task task = new Event(description, from, to);
        tasks.add(task);
        return task;
    }

    public Task markTaskAsDone(int index) throws BuddyException {
        Task task = getTask(index);
        task.markAsDone();
        return task;
    }

    public Task markTaskAsUndone(int index) throws BuddyException {
        Task task = getTask(index);
        task.markAsUndone();
        return task;
    }

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