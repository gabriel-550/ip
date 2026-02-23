import java.util.Scanner;

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
        Ui ui = new Ui();
        ui.showWelcome();

        Storage storage = new Storage("./data/enzo.txt");
        TaskList tasks = new TaskList();

        try {
            tasks = new TaskList(storage.load());
        } catch (EnzoException e) {
            ui.showLoadingError();
        }

        while (true) {
            try {
                String input = in.nextLine();

                if (input.equals("bye")) {
                    ui.showGoodbye();
                    break;

                } else if (input.equals("list")) {
                    ui.showList(tasks);

                } else if (input.startsWith("mark")) {
                    handleMark(input, tasks, ui);
                    storage.save(tasks.getTasks());

                } else if (input.startsWith("unmark")) {
                    handleUnmark(input, tasks, ui);
                    storage.save(tasks.getTasks());

                } else if (input.startsWith("delete")) {
                    handleDelete(input, tasks, ui);
                    storage.save(tasks.getTasks());

                } else if (input.startsWith("todo")) {
                    handleTodo(input, tasks, ui);
                    storage.save(tasks.getTasks());

                } else if (input.startsWith("deadline")) {
                    handleDeadline(input, tasks, ui);
                    storage.save(tasks.getTasks());

                } else if (input.startsWith("event")) {
                    handleEvent(input, tasks, ui);
                    storage.save(tasks.getTasks());

                } else {
                    throw new EnzoException("Sorry, I don't understand this command");
                }
            } catch (EnzoException e) {
                ui.showError(e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
                ui.showError("Hey! Task number is out of range.");
            }
        }
    }

    private static void handleMark(String input, TaskList tasks, Ui ui) throws EnzoException {
        if (input.length() <= MARK_PREFIX_LENGTH) {
            throw new EnzoException("Hey! Please specify which task to mark.");
        }

        int index = parseTaskIndex(input, MARK_PREFIX_LENGTH, tasks.size());
        tasks.get(index).mark();
        ui.showMarked(tasks.get(index));
    }

    private static void handleUnmark(String input, TaskList tasks, Ui ui) throws EnzoException {
        if (input.length() <= UNMARK_PREFIX_LENGTH) {
            throw new EnzoException("Hey! Please specify which task to unmark.");
        }

        int index = parseTaskIndex(input, UNMARK_PREFIX_LENGTH, tasks.size());
        tasks.get(index).unmark();
        ui.showUnmarked(tasks.get(index));
    }

    private static void handleDelete(String input, TaskList tasks, Ui ui) throws EnzoException {
        if (input.length() <= DELETE_PREFIX_LENGTH) {
            throw new EnzoException("Hey! Please specify which task to delete.");
        }

        int index = parseTaskIndex(input, DELETE_PREFIX_LENGTH, tasks.size());
        Task removedTask = tasks.remove(index);
        ui.showDeleted(removedTask, tasks.size());
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

    private static void handleTodo(String input, TaskList tasks, Ui ui) throws EnzoException {
        if (input.length() <= TODO_PREFIX_LENGTH) {
            throw new EnzoException("Hey! A todo needs a description and cannot be left empty");
        }

        String description = input.substring(TODO_PREFIX_LENGTH).trim();
        if (description.isEmpty()) {
            throw new EnzoException("Hey! A todo needs a description and cannot be left empty");
        }

        tasks.add(new Todo(description));
        ui.showTaskAdded(tasks);
    }

    private static void handleDeadline(String input, TaskList tasks, Ui ui) throws EnzoException {
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
        ui.showTaskAdded(tasks);
    }

    private static void handleEvent(String input, TaskList tasks, Ui ui) throws EnzoException {
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
        ui.showTaskAdded(tasks);
    }
}