/**
 * Represents a task with a description and a completion status.
 * Serves as the base class for specific task types such as
 * {@link Todo}, {@link Deadline}, and {@link Event}.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task with the given description, initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Marks the task as completed. */
    public void mark() {
        this.isDone = true;
    }

    /** Marks the task as not completed. */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns the status icon representing the task's completion state.
     *
     * @return {@code "X"} if done, {@code " "} otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
