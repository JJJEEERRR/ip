import java.util.Scanner;

public class Buddy {
    public static void main(String[] args) {
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
            }
            System.out.println(input);
        }

        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();
    }
}