import java.util.Scanner; //保留Scanner的import

public class Buddy {
    private static final int MAX_TASKS = 100;
    private String[] tasks = new String[MAX_TASKS];
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
            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                listTasks();
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
        for (int i = 0; i < taskCount; i++) {
            System.out.println("  " + (i + 1) + ". " + tasks[i]);
        }
        System.out.println("  ____________________________________________________________");
    }
}