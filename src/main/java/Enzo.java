import java.util.Scanner;

public class Enzo {
    public static final int MAX_TASKS = 100;
    public static final int MARK_PREFIX_LENGTH = 5;
    public static final int UNMARK_PREFIX_LENGTH = 7;
    public static final int DISPLAYED_INDEX_OFFSET = 1;
    public static final int TODO_PREFIX_LENGTH = 5;
    public static final int DEADLINE_PREFIX_LENGTH = 9;
    public static final int EVENT_PREFIX_LENGTH = 6;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        printWelcome();

        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        while (true) {
            String input = in.nextLine();

            if (input.equals("bye")) {
                printGoodbye();
                break;

            } else if (input.equals("list")) {
                handleList(tasks, taskCount);

            } else if (input.startsWith("mark ")) {
                handleMark(input, tasks);

            } else if (input.startsWith("unmark ")) {
                handleUnmark(input, tasks);

            } else if (input.startsWith("todo ")) {
                taskCount = handleTodo(input, tasks, taskCount);

            } else if (input.startsWith("deadline ")) {
                taskCount = handleDeadline(input, tasks, taskCount);

            } else if (input.startsWith("event ")) {
                taskCount = handleEvent(input, tasks, taskCount);

            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;

                System.out.println(" added: " + input);
            }
        }
    }

    private static void printWelcome() {
        System.out.println(" Hello! I'm Enzo");
        System.out.println(" What can I do for you?");
    }

    private static void printGoodbye() {
        System.out.println(" Bye. I’ll be right here when you need me again!");
    }

    private static void handleList(Task[] tasks, int taskCount) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println(" " + (i + 1) + "." + tasks[i]);
        }
    }

    private static void handleMark(String input, Task[] tasks) {
        int index = Integer.parseInt(input.substring(MARK_PREFIX_LENGTH)) - DISPLAYED_INDEX_OFFSET;
        tasks[index].mark();

        System.out.println("Nice! I've marked this task as done:");
        System.out.println(" " + tasks[index]);
    }

    private static void handleUnmark(String input, Task[] tasks) {
        int index = Integer.parseInt(input.substring(UNMARK_PREFIX_LENGTH)) - DISPLAYED_INDEX_OFFSET;
        tasks[index].unmark();

        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(" " + tasks[index]);
    }

    private static int handleTodo(String input, Task[] tasks, int taskCount) {
        String description = input.substring(TODO_PREFIX_LENGTH);
        tasks[taskCount++] = new Todo(description);
        printTaskAdded(tasks, taskCount);
        return taskCount;
    }

    private static int handleDeadline(String input, Task[] tasks, int taskCount) {
        String[] parts = input.substring(DEADLINE_PREFIX_LENGTH).split(" /by ");
        String description = parts[0];
        String by = parts[1];

        tasks[taskCount++] = new Deadline(description, by);
        printTaskAdded(tasks, taskCount);
        return taskCount;
    }

    private static int handleEvent(String input, Task[] tasks, int taskCount) {
        String[] parts = input.substring(EVENT_PREFIX_LENGTH).split(" /from | /to ");
        String description = parts[0];
        String from = parts[1];
        String to = parts[2];

        tasks[taskCount++] = new Event(description, from, to);
        printTaskAdded(tasks, taskCount);
        return taskCount;
    }

    private static void printTaskAdded(Task[] tasks, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println(" " + tasks[taskCount - 1]);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }
}