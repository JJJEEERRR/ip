package buddy.ui;

import buddy.data.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles the user interface for the Buddy application.
 * Responsible for displaying information to the user and collecting user input.
 */
public class Ui {
    private Scanner scanner;
    private static final String DIVIDER = "  ____________________________________________________________";
    private static final DateTimeFormatter DISPLAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy");

    /**
     * Creates a new UI instance.
     * Initializes the scanner for reading user input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Reads a command from the user input.
     *
     * @return The command entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays the welcome message when the application starts.
     */
    public void showWelcome() {
        String logo = "buddy.Buddy";
        System.out.println("Hello from\n" + logo);
        System.out.println("Hello! I'm buddy.Buddy");
        System.out.println("What can I do for you?");
    }

    /**
     * Displays the goodbye message when the application exits.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Displays an error message when task loading fails.
     *
     * @param message The error message to display.
     */
    public void showLoadingError(String message) {
        System.out.println("Error loading tasks: " + message);
        System.out.println("Starting with an empty task list.");
    }

    /**
     * Displays a general error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println(DIVIDER);
        System.out.println("  " + message);
        System.out.println(DIVIDER);
    }

    /**
     * Displays the list of tasks.
     *
     * @param tasks The list of tasks to display.
     */
    public void showTaskList(ArrayList<Task> tasks) {
        System.out.println(DIVIDER);
        System.out.println("  Here are the tasks in your list:");
        if (tasks.isEmpty()) {
            System.out.println("  Your task list is empty!");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + tasks.get(i));
            }
        }
        System.out.println(DIVIDER);
    }

    public void showAddedTask(Task task, int totalTasks) {
        System.out.println(DIVIDER);
        System.out.println("  Got it. I've added this task:");
        System.out.println("    " + task);
        System.out.println("  Now you have " + totalTasks + " tasks in the list.");
        System.out.println(DIVIDER);
    }

    public void showMarkedTask(Task task) {
        System.out.println(DIVIDER);
        System.out.println("  Nice! I've marked this task as done:");
        System.out.println("    " + task);
        System.out.println(DIVIDER);
    }

    public void showUnmarkedTask(Task task) {
        System.out.println(DIVIDER);
        System.out.println("  OK, I've marked this task as not done yet:");
        System.out.println("    " + task);
        System.out.println(DIVIDER);
    }

    public void showDeletedTask(Task task, int totalTasks) {
        System.out.println(DIVIDER);
        System.out.println("  Noted. I've removed this task:");
        System.out.println("    " + task);
        System.out.println("  Now you have " + totalTasks + " tasks in the list.");
        System.out.println(DIVIDER);
    }

    public void showFoundTasks(ArrayList<Task> tasks) {
        System.out.println(DIVIDER);
        if (tasks.isEmpty()) {
            System.out.println("  No matching tasks found.");
        } else {
            System.out.println("  Here are the matching tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + tasks.get(i));
            }
        }
        System.out.println(DIVIDER);
    }

    public void showTasksOnDate(ArrayList<Task> tasks, LocalDateTime date) {
        System.out.println(DIVIDER);
        System.out.println("  Tasks on " + date.format(DISPLAY_DATE_FORMATTER) + ":");
        if (tasks.isEmpty()) {
            System.out.println("  No tasks found on this date.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + tasks.get(i));
            }
        }
        System.out.println(DIVIDER);
    }

    public void showLine() {
        System.out.println(DIVIDER);
    }

    public void closeScanner() {
        scanner.close();
    }
}