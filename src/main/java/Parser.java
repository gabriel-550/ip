/**
 * Parses user input into commands and task objects for Enzo.
 */

public class Parser {
    public static final int MARK_PREFIX_LENGTH = 5;
    public static final int UNMARK_PREFIX_LENGTH = 7;
    public static final int DISPLAYED_INDEX_OFFSET = 1;
    public static final int TODO_PREFIX_LENGTH = 5;
    public static final int DEADLINE_PREFIX_LENGTH = 9;
    public static final int EVENT_PREFIX_LENGTH = 6;
    public static final int DELETE_PREFIX_LENGTH = 7;
    public static final int FIND_PREFIX_LENGTH = 5;

    /**
     * Parses the task index for a mark command.
     *
     * @param input The full user input string.
     * @param tasks The current task list.
     * @return The index of the task to mark.
     * @throws EnzoException If the index is missing, non-numerical, or out of range.
     */
    public static int parseMarkIndex(String input, TaskList tasks) throws EnzoException {
        if (input.length() <= MARK_PREFIX_LENGTH) {
            throw new EnzoException("Hey! Please specify which task to mark.");
        }
        return parseTaskIndex(input, MARK_PREFIX_LENGTH, tasks.size());
    }

    /**
     * Parses the task index for an unmark command.
     *
     * @param input The full user input string.
     * @param tasks The current task list.
     * @return The index of the task to unmark.
     * @throws EnzoException If the index is missing, non-numerical, or out of range.
     */
    public static int parseUnmarkIndex(String input, TaskList tasks) throws EnzoException {
        if (input.length() <= UNMARK_PREFIX_LENGTH) {
            throw new EnzoException("Hey! Please specify which task to unmark.");
        }
        return parseTaskIndex(input, UNMARK_PREFIX_LENGTH, tasks.size());
    }

    /**
     * Parses the task index for a delete command.
     *
     * @param input The full user input string.
     * @param tasks The current task list.
     * @return The index of the task to delete.
     * @throws EnzoException If the index is missing, non-numerical, or out of range.
     */
    public static int parseDeleteIndex(String input, TaskList tasks) throws EnzoException {
        if (input.length() <= DELETE_PREFIX_LENGTH) {
            throw new EnzoException("Hey! Please specify which task to delete.");
        }
        return parseTaskIndex(input, DELETE_PREFIX_LENGTH, tasks.size());
    }

    /**
     * Parses a Todo task from user input.
     *
     * @param input The full user input string.
     * @return The constructed Todo task.
     * @throws EnzoException If the description is missing or empty.
     */
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

    /**
     * Parses a Deadline task from user input.
     * Expects the format: {@code deadline <description> /by <date>}
     *
     * @param input The full user input string.
     * @return The constructed Deadline task.
     * @throws EnzoException If the /by label is missing or the description is empty.
     */
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

    /**
     * Parses an Event task from user input.
     * Expects the format: {@code event <description> /from <start> /to <end>}
     *
     * @param input The full user input string.
     * @return The constructed Event task.
     * @throws EnzoException If /from or /to labels are missing, or the description is empty.
     */
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

    /**
     * Parses the search keyword from a find command.
     *
     * @param input The full user input string.
     * @return The keyword to search for.
     * @throws EnzoException If the keyword is missing or empty.
     */
    public static String parseFind(String input) throws EnzoException {
        if (input.length() <= FIND_PREFIX_LENGTH) {
            throw new EnzoException("Hey! Please specify a keyword to search for.");
        }
        String keyword = input.substring(FIND_PREFIX_LENGTH).trim();
        if (keyword.isEmpty()) {
            throw new EnzoException("Hey! Please specify a keyword to search for.");
        }
        return keyword;
    }

    /**
     * Extracts the command keyword from the user input string.
     *
     * @param input The full user input string.
     * @return The command keyword in lowercase.
     */
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
