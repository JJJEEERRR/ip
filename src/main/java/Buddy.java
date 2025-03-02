import java.util.ArrayList;
import java.util.Scanner;

public class Buddy {
    private ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        new Buddy().run();
    }

    public void run() {
        String logo = "Buddy";

//                "  /\\_/\\  \n"
//                + " ( o.o ) \n"
//                + "  > ^ <  \n";


        System.out.println("Hello from\n" + logo);
        System.out.println("Hello! I'm Buddy");
        System.out.println("What can I do for you?");

        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine();
            String[] parts = input.split(" ", 2); // 将输入分割为命令和描述
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
                default:
                    System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }

    private void addTask(String command, String description) {
        Task task = null;
        switch (command) {
            case "todo":
                task = new Todo(description);
                break;
            case "deadline":
                String[] deadlineParts = description.split(" /by ");
                task = new Deadline(deadlineParts[0], deadlineParts[1]);
                break;
            case "event":
                String[] eventParts = description.split(" /from | /to ");
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
            } else {
                System.out.println("Invalid task index.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid task number.");
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
            } else {
                System.out.println("Invalid task index.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid task number.");
        }
    }
}