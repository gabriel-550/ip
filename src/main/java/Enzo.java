import java.util.Scanner;

public class Enzo {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println(" Hello! I'm Enzo");
        System.out.println(" What can I do for you?");

        while (true) {
            String input = in.nextLine();
            if (input.equals("bye")) {
                System.out.println(" Bye. I’ll be right here when you need me again!");
                break;
            }
            System.out.println(" " + input);
        }

    }
}