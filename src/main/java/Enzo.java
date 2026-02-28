import java.util.Scanner;

public class Enzo {
    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

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

    public static void main(String[] args) {
        new Enzo("./data/enzo.txt").run();
    }
}