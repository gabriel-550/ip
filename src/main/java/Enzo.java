import java.util.Scanner;

/**
 * Represents the main class of the Enzo application.
 * Handles initialization of core components and runs the main command loop.
 */
public class Enzo {
    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

    /**
     * Constructs an Enzo instance, initializing UI, storage, and task list.
     * If loading from file fails, starts with an empty task list.
     *
     * @param filePath Path to the file used for persistent task storage.
     */
    public Enzo(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (EnzoException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main command loop, reading and processing user input
     * until the user exits the application.
     */
    public void run() {
        Scanner in = new Scanner(System.in);
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String input = in.nextLine();
                String command = Parser.extractCommand(input);

                switch (command) {
                case "bye":
                    ui.showGoodbye();
                    isExit = true;
                    break;
                case "list":
                    ui.showList(tasks);
                    break;
                case "mark":
                    int markIndex = Parser.parseMarkIndex(input, tasks);
                    tasks.get(markIndex).mark();
                    ui.showMarked(tasks.get(markIndex));
                    storage.save(tasks.getTasks());
                    break;
                case "unmark":
                    int unmarkIndex = Parser.parseUnmarkIndex(input, tasks);
                    tasks.get(unmarkIndex).unmark();
                    ui.showUnmarked(tasks.get(unmarkIndex));
                    storage.save(tasks.getTasks());
                    break;
                case "delete":
                    int deleteIndex = Parser.parseDeleteIndex(input, tasks);
                    Task removed = tasks.remove(deleteIndex);
                    ui.showDeleted(removed, tasks.size());
                    storage.save(tasks.getTasks());
                    break;
                case "find":
                    String keyword = Parser.parseFind(input);
                    ui.showFound(tasks.find(keyword));
                    break;
                case "todo":
                    tasks.add(Parser.parseTodo(input));
                    ui.showTaskAdded(tasks);
                    storage.save(tasks.getTasks());
                    break;
                case "deadline":
                    tasks.add(Parser.parseDeadline(input));
                    ui.showTaskAdded(tasks);
                    storage.save(tasks.getTasks());
                    break;
                case "event":
                    tasks.add(Parser.parseEvent(input));
                    ui.showTaskAdded(tasks);
                    storage.save(tasks.getTasks());
                    break;
                default:
                    throw new EnzoException("Sorry, I don't understand this command");
                }
            } catch (EnzoException e) {
                ui.showError(e.getMessage());
            }
            ui.showDivider();
        }
    }

    /**
     * Entry point of the Enzo application.
     * Creates an Enzo instance with a data file path and starts the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        new Enzo("./data/enzo.txt").run();
    }
}