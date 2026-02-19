import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws EnzoException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        File directory = file.getParentFile();
        if (directory != null && !directory.exists()) {
            throw new EnzoException("Failed to create data directory");
        }

        if (!file.exists()) {
            return tasks;
        }

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            scanner.close();
        } catch (IOException e) {
            throw new EnzoException("Error loading tasks from file: " + e.getMessage());
        }

        return tasks;
    }

    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];
        Task task = null;

        switch (type) {
        case "T":
            task = new Todo(description);
            break;
        case "D":
            if (parts.length >= 4) {
                task = new Deadline(description, parts[3]);
            }
            break;
        case "E":
            if (parts.length >= 5) {
                task = new Event(description, parts[3], parts[4]);
            }
            break;
        default:
            return null;
        }

        if (task != null && isDone) {
            task.mark();
        }

        return task;
    }

    public void save(ArrayList<Task> tasks) throws EnzoException {
        try {
            File file = new File(filePath);
            File directory = file.getParentFile();
            if (directory != null && !directory.exists()) {
                throw new EnzoException("Failed to create data directory");
            }

            FileWriter writer = new FileWriter(file);
            for (Task task : tasks) {
                writer.write(taskToFileFormat(task) + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            throw new EnzoException("Error saving tasks to file: " + e.getMessage());
        }
    }

    private String taskToFileFormat(Task task) {
        String type;
        String details = "";

        if (task instanceof Todo) {
            type = "T";
        } else if (task instanceof Deadline) {
            type = "D";
            Deadline deadline = (Deadline) task;
            details = " | " + deadline.by;
        } else if (task instanceof Event) {
            type = "E";
            Event event = (Event) task;
            details = " | " + event.from + " | " + event.to;
        } else {
            type = "T"; // Default fallback
        }

        String isDone = task.isDone ? "1" : "0";
        return type + " | " + isDone + " | " + task.description + details;
    }
}
