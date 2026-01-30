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
                    Task task = tasks[i];
                    System.out.println(" " + (i + 1) + ".[" + task.getStatusIcon() + "] " + task.getDescription());
                }

            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                tasks[index].mark();

                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("  [X] " + tasks[index].getDescription());

            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                tasks[index].unmark();

                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("  [ ] " + tasks[index].getDescription());

            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;

                System.out.println(" added: " + input);
            }
        }
    }
}