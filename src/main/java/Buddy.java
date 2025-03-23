import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Buddy {
    private ArrayList<Task> tasks = new ArrayList<>();
    private static final String DATA_FOLDER = "./data";
    private static final String DATA_FILE = DATA_FOLDER + "/buddy.txt";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy");

    public static void main(String[] args) {
        new Buddy().run();
    }

    public void run() {
        String logo = "Buddy";

        System.out.println("Hello from\n" + logo);
        System.out.println("Hello! I'm Buddy");

        // Load tasks from file when starting up
        loadTasks();

        System.out.println("What can I do for you?");

        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine();
            String[] parts = input.split(" ", 2); // Split input into command and description
            String command = parts[0];
            String description = parts.length > 1 ? parts[1] : "";

            switch (command) {
                case "bye":
                    System.out.println("Bye. Hope to see you again soon!");
                    scanner.close();
                    return;
                case "list":
                    listTasks();
                    break;
                case "mark":
                    markTask(parts);
                    break;
                case "unmark":
                    unmarkTask(parts);
                    break;
                case "todo":
                case "deadline":
                case "event":
                    addTask(command, description);
                    break;
                case "delete":
                    deleteTask(parts);
                    break;
                case "find":
                    if (description.trim().isEmpty()) {
                        System.out.println("  ____________________________________________________________");
                        System.out.println("  Please provide a keyword to search for.");
                        System.out.println("  ____________________________________________________________");
                    } else {
                        findTasks(description);
                    }
                    break;
                case "date":
                    if (description.trim().isEmpty()) {
                        System.out.println("  ____________________________________________________________");
                        System.out.println("  Please provide a date in d/M/yyyy format.");
                        System.out.println("  ____________________________________________________________");
                    } else {
                        listTasksOnDate(description);
                    }
                    break;
                default:
                    System.out.println("  ____________________________________________________________");
                    System.out.println("  Uh-oh! That command went right over my head. Mind trying again?");
                    System.out.println("  ____________________________________________________________");
            }
        }
    }

    private void loadTasks() {
        try {
            // Ensure directory exists
            File directory = new File(DATA_FOLDER);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Check if file exists, if not create a new one
            File file = new File(DATA_FILE);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("No existing task data. Starting fresh!");
                return;
            }

            // Read file and load tasks
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (!line.trim().isEmpty()) {
                    Task task = parseTaskFromFileLine(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                }
            }
            fileScanner.close();
            System.out.println("Tasks loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }

    private Task parseTaskFromFileLine(String line) {
        try {
            String[] parts = line.split(" \\| ");
            if (parts.length < 3) {
                System.out.println("Warning: Corrupted task data: " + line);
                return null;
            }

            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            Task task = null;
            switch (type) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    if (parts.length < 4) {
                        System.out.println("Warning: Corrupted deadline data: " + line);
                        return null;
                    }
                    task = new Deadline(description, parts[3]);
                    break;
                case "E":
                    if (parts.length < 5) {
                        System.out.println("Warning: Corrupted event data: " + line);
                        return null;
                    }
                    task = new Event(description, parts[3], parts[4]);
                    break;
                default:
                    System.out.println("Warning: Unknown task type: " + type);
                    return null;
            }

            if (isDone) {
                task.markAsDone();
            }
            return task;
        } catch (Exception e) {
            System.out.println("Warning: Error parsing task data: " + line);
            return null;
        }
    }

    private void saveTasks() {
        try {
            // Ensure directory exists
            File directory = new File(DATA_FOLDER);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Write tasks to file
            FileWriter fileWriter = new FileWriter(DATA_FILE);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            for (Task task : tasks) {
                writer.write(convertTaskToFileLine(task));
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private String convertTaskToFileLine(Task task) {
        StringBuilder sb = new StringBuilder();

        // Task type
        if (task instanceof Todo) {
            sb.append("T");
        } else if (task instanceof Deadline) {
            sb.append("D");
        } else if (task instanceof Event) {
            sb.append("E");
        }

        // Completion status
        sb.append(" | ").append(task.isDone ? "1" : "0");

        // Description
        sb.append(" | ").append(task.description);

        // Additional data based on task type
        if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            sb.append(" | ").append(deadline.getByForStorage());
        } else if (task instanceof Event) {
            Event event = (Event) task;
            sb.append(" | ").append(event.getFromForStorage());
            sb.append(" | ").append(event.getToForStorage());
        }

        return sb.toString();
    }

    private void addTask(String command, String description) {
        // Check if task description is empty
        if (description.trim().isEmpty()) {
            System.out.println("  ____________________________________________________________");
            System.out.println("  Whoops! You forgot to tell me what the task is! ");
            System.out.println("  ____________________________________________________________");
            return;
        }

        Task task = null;
        switch (command) {
            case "todo":
                task = new Todo(description);
                break;
            case "deadline":
                String[] deadlineParts = description.split(" /by ");
                if (deadlineParts.length != 2) {
                    System.out.println("  ____________________________________________________________");
                    System.out.println("  OOPS!!! Invalid deadline format. Please use: deadline <description> /by <date>");
                    System.out.println("  Example: deadline return book /by 2/12/2023 1800");
                    System.out.println("  ____________________________________________________________");
                    return;
                }
                // Create deadline with date
                task = new Deadline(deadlineParts[0], deadlineParts[1]);
                break;
            case "event":
                String[] eventParts = description.split(" /from | /to ");
                if (eventParts.length != 3) {
                    System.out.println("  ____________________________________________________________");
                    System.out.println("  OOPS!!! Invalid event format. Please use: event <description> /from <start> /to <end>");
                    System.out.println("  Example: event project meeting /from 6/8/2023 1400 /to 6/8/2023 1600");
                    System.out.println("  ____________________________________________________________");
                    return;
                }
                // Create event with dates
                task = new Event(eventParts[0], eventParts[1], eventParts[2]);
                break;
        }
        if (task != null) {
            tasks.add(task);
            System.out.println("  ____________________________________________________________");
            System.out.println("  Got it. I've added this task:");
            System.out.println("    " + task);
            System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
            System.out.println("  ____________________________________________________________");
            saveTasks(); // Save after adding
        }
    }

    private void listTasks() {
        System.out.println("  ____________________________________________________________");
        System.out.println("  Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + tasks.get(i));
        }
        System.out.println("  ____________________________________________________________");
    }

    private void markTask(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Please provide a task number to mark.");
            return;
        }
        try {
            int taskIndex = Integer.parseInt(parts[1]) - 1;
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                Task task = tasks.get(taskIndex);
                task.markAsDone();
                System.out.println("  ____________________________________________________________");
                System.out.println("  Nice! I've marked this task as done:");
                System.out.println("    " + task);
                System.out.println("  ____________________________________________________________");
                saveTasks(); // Save after marking
            } else {
                System.out.println("  ____________________________________________________________");
                System.out.println("  OOPS!!! Invalid task index.");
                System.out.println("  ____________________________________________________________");
            }
        } catch (NumberFormatException e) {
            System.out.println("  ____________________________________________________________");
            System.out.println("  OOPS!!! Invalid task number.");
            System.out.println("  ____________________________________________________________");
        }
    }

    private void unmarkTask(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Please provide a task number to unmark.");
            return;
        }
        try {
            int taskIndex = Integer.parseInt(parts[1]) - 1;
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                Task task = tasks.get(taskIndex);
                task.markAsUndone();
                System.out.println("  ____________________________________________________________");
                System.out.println("  OK, I've marked this task as not done yet:");
                System.out.println("    " + task);
                System.out.println("  ____________________________________________________________");
                saveTasks(); // Save after unmarking
            } else {
                System.out.println("Invalid task index.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid task number.");
        }
    }

    private void deleteTask(String[] parts) {
        if (parts.length < 2) {
            System.out.println("  ____________________________________________________________");
            System.out.println("  Please provide a task number to delete.");
            System.out.println("  ____________________________________________________________");
            return;
        }
        try {
            int taskIndex = Integer.parseInt(parts[1]) - 1;
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                Task removedTask = tasks.remove(taskIndex);
                System.out.println("  ____________________________________________________________");
                System.out.println("  Noted. I've removed this task:");
                System.out.println("    " + removedTask);
                System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
                System.out.println("  ____________________________________________________________");
                saveTasks(); // Save after deleting
            } else {
                System.out.println("  ____________________________________________________________");
                System.out.println("  OOPS!!! Invalid task index.");
                System.out.println("  ____________________________________________________________");
            }
        } catch (NumberFormatException e) {
            System.out.println("  ____________________________________________________________");
            System.out.println("  OOPS!!! Invalid task number.");
            System.out.println("  ____________________________________________________________");
        }
    }

    private void findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.description.toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }

        System.out.println("  ____________________________________________________________");
        if (matchingTasks.isEmpty()) {
            System.out.println("  No matching tasks found.");
        } else {
            System.out.println("  Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + matchingTasks.get(i));
            }
        }
        System.out.println("  ____________________________________________________________");
    }

    private void listTasksOnDate(String dateString) {
        LocalDateTime searchDate;
        try {
            // Parse the date string to a LocalDateTime
            searchDate = LocalDateTime.parse(dateString + " 0000", DATE_FORMATTER.ofPattern("d/M/yyyy HHmm"));
        } catch (DateTimeParseException e) {
            System.out.println("  ____________________________________________________________");
            System.out.println("  Invalid date format. Please use d/M/yyyy format (e.g., 2/12/2023)");
            System.out.println("  ____________________________________________________________");
            return;
        }

        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (deadline.isOnDate(searchDate)) {
                    matchingTasks.add(task);
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                if (event.isOnDate(searchDate)) {
                    matchingTasks.add(task);
                }
            }
        }

        System.out.println("  ____________________________________________________________");
        System.out.println("  Tasks on " + searchDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ":");
        if (matchingTasks.isEmpty()) {
            System.out.println("  No tasks found on this date.");
        } else {
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + matchingTasks.get(i));
            }
        }
        System.out.println("  ____________________________________________________________");
    }
}