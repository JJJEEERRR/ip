import java.util.Scanner;

public class Buddy {
    private static final int MAX_TASKS = 100;
    private String[] tasks = new String[MAX_TASKS];
    private boolean[] taskDone = new boolean[MAX_TASKS]; //. 用于跟踪任务完成状态的数组
    private int taskCount = 0;

    public static void main(String[] args) {
        new Buddy().run();
    }

    public void run() {
        String logo = """
                 /\\_/\\ \s
                ( o.o )\s
                 > ^ < \s
                """;
        System.out.println("Hello from\n" + logo);
        System.out.println("Hello! I'm Buddy");
        System.out.println("What can I do for you?");

        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine();
            String[] parts = input.split(" "); // 将输入按空格分割成命令和参数
            String command = parts[0]; // 获取命令

            if (command.equals("bye")) {
                break;
            } else if (command.equals("list")) {
                listTasks();
            } else if (command.equals("mark")) { // 处理 mark 命令
                markTask(parts);
            } else if (command.equals("unmark")) { //处理 unmark 命令
                unmarkTask(parts);
            } else {
                addTask(input);
            }
        }

        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();
    }

    private void addTask(String task) {
        if (taskCount < MAX_TASKS) {
            tasks[taskCount] = task;
            taskDone[taskCount] = false;
            taskCount++;
            System.out.println("  ____________________________________________________________");
            System.out.println("    added: " + task);
            System.out.println("  ____________________________________________________________");
        } else {
            System.out.println("Task list is full.");
        }
    }

    private void listTasks() {
        System.out.println("  ____________________________________________________________");
        System.out.println("  Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            String done = taskDone[i] ? "[X]" : "[ ]";
            System.out.println("  " + (i + 1) + "." + done + " " + tasks[i]);
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
            if (taskIndex >= 0 && taskIndex < taskCount) {
                taskDone[taskIndex] = true;
                System.out.println("  ____________________________________________________________");
                System.out.println("  Nice! I've marked this task as done:");
                System.out.println("    [X] " + tasks[taskIndex]);
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
            if (taskIndex >= 0 && taskIndex < taskCount) {
                taskDone[taskIndex] = false;
                System.out.println("  ____________________________________________________________");
                System.out.println("  OK, I've marked this task as not done yet:");
                System.out.println("    [ ] " + tasks[taskIndex]);
                System.out.println("  ____________________________________________________________");
            } else {
                System.out.println("Invalid task index.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid task number.");
        }
    }
}