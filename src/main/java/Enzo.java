import java.util.Scanner;
import java.util.ArrayList;

public class Enzo {
    public static final int MARK_PREFIX_LENGTH = 5;
    public static final int UNMARK_PREFIX_LENGTH = 7;
    public static final int DISPLAYED_INDEX_OFFSET = 1;
    public static final int TODO_PREFIX_LENGTH = 5;
    public static final int DEADLINE_PREFIX_LENGTH = 9;
    public static final int EVENT_PREFIX_LENGTH = 6;
    public static final int DELETE_PREFIX_LENGTH = 7;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        printWelcome();

        ArrayList<Task> tasks = new ArrayList<>();

        while (true) {
            try {
                String input = in.nextLine();

                if (input.equals("bye")) {
                    printGoodbye();
                    break;

                } else if (input.equals("list")) {
                    handleList(tasks);

                } else if (input.startsWith("mark")) {
                    handleMark(input, tasks);

                } else if (input.startsWith("unmark")) {
                    handleUnmark(input, tasks);

                } else if (input.startsWith("delete")) {
                    handleDelete(input, tasks);

                } else if (input.startsWith("todo")) {
                    handleTodo(input, tasks);

                } else if (input.startsWith("deadline")) {
                    handleDeadline(input, tasks);

                } else if (input.startsWith("event")) {
                    handleEvent(input, tasks);

                } else {
                    throw new EnzoException("Sorry, I don't understand this command");
                }
            } catch (EnzoException e) {
                System.out.println(e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Hey! Task number is out of range.");
            }
        }
    }

    private static void printWelcome() {
        System.out.println(" Hello! I'm Enzo");
        System.out.println(" How can I help you today?");
    }

    private static void printGoodbye() {
        System.out.println(" Bye. I’ll be right here when you need me again!");
    }

    private static void handleList(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }
    }

    private static void handleMark(String input, ArrayList<Task> tasks) throws EnzoException {
        if (input.length() <= MARK_PREFIX_LENGTH) {
            throw new EnzoException("Hey! Please specify which task to mark.");
        }

        int index = parseTaskIndex(input, MARK_PREFIX_LENGTH, tasks.size());
        tasks.get(index).mark();

        System.out.println("Nice! I've marked this task as done:");
        System.out.println(" " + tasks.get(index));
    }

    private static void handleUnmark(String input, ArrayList<Task> tasks) throws EnzoException {
        if (input.length() <= UNMARK_PREFIX_LENGTH) {
            throw new EnzoException("Hey! Please specify which task to unmark.");
        }

        int index = parseTaskIndex(input, UNMARK_PREFIX_LENGTH, tasks.size());
        tasks.get(index).unmark();

        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(" " + tasks.get(index));
    }

    private static void handleDelete(String input, ArrayList<Task> tasks) throws EnzoException {
        if (input.length() <= DELETE_PREFIX_LENGTH) {
            throw new EnzoException("Hey! Please specify which task to delete.");
        }

        int index = parseTaskIndex(input, DELETE_PREFIX_LENGTH, tasks.size());
        Task removedTask = tasks.remove(index);

        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removedTask);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
    }

    private static int parseTaskIndex(String input, int prefixLength, int taskCount) throws EnzoException {
        try {
            String numberPart = input.substring(prefixLength).trim();
            if (numberPart.isEmpty()) {
                throw new EnzoException("Hey! Please provide a task number.");
            }
            int index = Integer.parseInt(numberPart) - DISPLAYED_INDEX_OFFSET;
            if (index < 0 || index >= taskCount) {
                throw new EnzoException("Hey! Task number is out of range.");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new EnzoException("Hey! Please provide a valid task number.");
        }
    }

    private static void handleTodo(String input, ArrayList<Task> tasks) throws EnzoException {
        if (input.length() <= TODO_PREFIX_LENGTH) {
            throw new EnzoException("Hey! A todo needs a description and cannot be left empty");
        }

        String description = input.substring(TODO_PREFIX_LENGTH).trim();
        if (description.isEmpty()) {
            throw new EnzoException("Hey! A todo needs a description and cannot be left empty");
        }

        tasks.add(new Todo(description));
        printTaskAdded(tasks);
    }

    private static void handleDeadline(String input, ArrayList<Task> tasks) throws EnzoException {
        if (!input.contains(" /by ")) {
            throw new EnzoException("Hey! Please specify the deadline with /by");
        }

        String[] parts = input.substring(DEADLINE_PREFIX_LENGTH).split(" /by ");
        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty()) {
            throw new EnzoException("Hey! Deadlines need a description and cannot be left empty");
        }

        tasks.add(new Deadline(description, by));
        printTaskAdded(tasks);
    }

    private static void handleEvent(String input, ArrayList<Task> tasks) throws EnzoException {
        if (!input.contains(" /from ") || !input.contains(" /to ")) {
            throw new EnzoException("Hey! Events need /from and /to details");
        }

        String[] parts = input.substring(EVENT_PREFIX_LENGTH).split(" /from | /to ");
        String description = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();

        if (description.isEmpty()) {
            throw new EnzoException("Hey! Events need a description and cannot be left empty");
        }

        tasks.add(new Event(description, from, to));
        printTaskAdded(tasks);
    }

    private static void printTaskAdded(ArrayList<Task> tasks) {
        System.out.println("Got it. I've added this task:");
        System.out.println(" " + tasks.get(tasks.size() - 1));
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
    }
}