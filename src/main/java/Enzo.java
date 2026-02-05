import java.util.Scanner;

public class Enzo {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println(" Hello! I'm Enzo");
        System.out.println(" What can I do for you?");

        while (true) {
            String input = in.nextLine();

            if (input.equals("bye")) {
                System.out.println(" Bye. I’ll be right here when you need me again!");
                break;

            } else if (input.equals("list")) {
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + "." + tasks[i]);
                }

            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                tasks[index].mark();

                System.out.println("Nice! I've marked this task as done:");
                System.out.println(" " + tasks[index]);

            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                tasks[index].unmark();

                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(" " + tasks[index]);

            } else if (input.startsWith("todo ")) {
                String description = input.substring(5);
                tasks[taskCount++] = new Todo(description);

                System.out.println("Got it. I've added this task:");
                System.out.println(" " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");

            } else if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).split(" /by ");
                String description = parts[0];
                String by = parts[1];

                tasks[taskCount++] = new Deadline(description, by);

                System.out.println("Got it. I've added this task:");
                System.out.println(" " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");

            } else if (input.startsWith("event ")) {
                String[] parts = input.substring(6).split(" /from | /to ");
                String description = parts[0];
                String from = parts[1];
                String to = parts[2];

                tasks[taskCount++] = new Event(description, from, to);

                System.out.println("Got it. I've added this task:");
                System.out.println(" " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");

            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;

                System.out.println(" added: " + input);
            }
        }
    }
}