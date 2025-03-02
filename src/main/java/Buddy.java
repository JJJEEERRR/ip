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
                case "delete": // 新增 delete 命令
                    deleteTask(parts);
                    break;
                default:
                    System.out.println("  ____________________________________________________________");
                    System.out.println("  Uh-oh! That command went right over my head. Mind trying again?");
                    System.out.println("  ____________________________________________________________");
            }
        }
    }

    private void addTask(String command, String description) {
        // 检查任务描述是否为空
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
                    System.out.println("  ____________________________________________________________");
                    return;
                }
                // 可以添加日期格式验证
                task = new Deadline(deadlineParts[0], deadlineParts[1]);
                break;
            case "event":
                String[] eventParts = description.split(" /from | /to ");
                if (eventParts.length != 3) {
                    System.out.println("  ____________________________________________________________");
                    System.out.println("  OOPS!!! Invalid event format. Please use: event <description> /from <start> /to <end>");
                    System.out.println("  ____________________________________________________________");
                    return;
                }
                // 可以添加日期格式验证
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
}