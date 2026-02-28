public class Parser {
    public static final int MARK_PREFIX_LENGTH = 5;
    public static final int UNMARK_PREFIX_LENGTH = 7;
    public static final int DISPLAYED_INDEX_OFFSET = 1;
    public static final int TODO_PREFIX_LENGTH = 5;
    public static final int DEADLINE_PREFIX_LENGTH = 9;
    public static final int EVENT_PREFIX_LENGTH = 6;
    public static final int DELETE_PREFIX_LENGTH = 7;

    public static int parseMarkIndex(String input, TaskList tasks) throws EnzoException {
        if (input.length() <= MARK_PREFIX_LENGTH) {
            throw new EnzoException("Hey! Please specify which task to mark.");
        }
        return parseTaskIndex(input, MARK_PREFIX_LENGTH, tasks.size());
    }

    public static int parseUnmarkIndex(String input, TaskList tasks) throws EnzoException {
        if (input.length() <= UNMARK_PREFIX_LENGTH) {
            throw new EnzoException("Hey! Please specify which task to unmark.");
        }
        return parseTaskIndex(input, UNMARK_PREFIX_LENGTH, tasks.size());
    }

    public static int parseDeleteIndex(String input, TaskList tasks) throws EnzoException {
        if (input.length() <= DELETE_PREFIX_LENGTH) {
            throw new EnzoException("Hey! Please specify which task to delete.");
        }
        return parseTaskIndex(input, DELETE_PREFIX_LENGTH, tasks.size());
    }

    public static Todo parseTodo(String input) throws EnzoException {
        if (input.length() <= TODO_PREFIX_LENGTH) {
            throw new EnzoException("Hey! A todo needs a description and cannot be left empty");
        }
        String description = input.substring(TODO_PREFIX_LENGTH).trim();
        if (description.isEmpty()) {
            throw new EnzoException("Hey! A todo needs a description and cannot be left empty");
        }
        return new Todo(description);
    }

    public static Deadline parseDeadline(String input) throws EnzoException {
        if (!input.contains(" /by ")) {
            throw new EnzoException("Hey! Please specify the deadline with /by");
        }
        String[] parts = input.substring(DEADLINE_PREFIX_LENGTH).split(" /by ");
        String description = parts[0].trim();
        String by = parts[1].trim();
        if (description.isEmpty()) {
            throw new EnzoException("Hey! Deadlines need a description and cannot be left empty");
        }
        return new Deadline(description, by);
    }

    public static Event parseEvent(String input) throws EnzoException {
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
        return new Event(description, from, to);
    }

    public static String extractCommand(String input) {
        return input.split(" ")[0].toLowerCase();
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
}
