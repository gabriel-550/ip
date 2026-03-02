/**
 * Handles all display messages for Enzo.
 */

public class Ui {

    /** Displays the welcome message when the application starts. */
    public void showWelcome() {
        System.out.println(" Hello! I'm Enzo");
        System.out.println(" How can I help you today?");
    }

    /** Displays the goodbye message when the user exits. */
    public void showGoodbye() {
        System.out.println(" Bye. I'll be right here when you need me again!");
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /** Displays an error message when tasks fail to load from storage. */
    public void showLoadingError() {
        System.out.println("Error loading tasks from file.");
    }

    /**
     * Displays all tasks currently in the task list.
     *
     * @param tasks The task list to display.
     */
    public void showList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Displays a confirmation that a task has been marked as done.
     *
     * @param task The task that was marked.
     */
    public void showMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(" " + task);
    }

    /**
     * Displays a confirmation that a task has been marked as not done.
     *
     * @param task The task that was unmarked.
     */
    public void showUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(" " + task);
    }

    /**
     * Displays a confirmation that a task has been deleted, along with the remaining count.
     *
     * @param task The task that was deleted.
     * @param remainingCount The number of tasks remaining in the list.
     */
    public void showDeleted(Task task, int remainingCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + remainingCount + " tasks in the list.");
    }

    /**
     * Displays tasks that match a searched keyword.
     * Displays a message if no tasks match.
     *
     * @param tasks The task list of matching tasks.
     */
    public void showFound(TaskList tasks) {
        if (tasks.size() == 0) {
            System.out.println("No matching tasks found.");
            return;
        }
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Displays a confirmation that a task has been added, along with the updated count.
     *
     * @param tasks The task list after the new task was added.
     */
    public void showTaskAdded(TaskList tasks) {
        System.out.println("Got it. I've added this task:");
        System.out.println(" " + tasks.get(tasks.size() - 1));
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
    }

    /** Displays a divider line to separate command outputs. */
    public void showDivider() {
        System.out.println("____________________________________________________________");
    }
}
