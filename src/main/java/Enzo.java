import java.util.Scanner;

public class Enzo {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String[] tasks = new String[100];
        int taskCount = 0;

        System.out.println(" Hello! I'm Enzo");
        System.out.println(" What can I do for you?");

        while (true) {
            String input = in.nextLine();

            if (input.equals("bye")) {
                System.out.println(" Bye. I’ll be right here when you need me again!");
                break;
            } else if (input.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
            } else {
                tasks[taskCount] = input;
                taskCount++;

                System.out.println(" added: " + input);
            }
        }
    }
}