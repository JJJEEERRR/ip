import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;
    private final String directory;

    public Storage(String filePath) {
        this.filePath = filePath;
        this.directory = filePath.substring(0, filePath.lastIndexOf('/'));
    }

    public ArrayList<Task> load() throws BuddyException {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            // Ensure directory exists
            createDirectoryIfNeeded();

            // Check if file exists, if not create a new one
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
                return tasks;
            }

            // Read file and load tasks
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (!line.trim().isEmpty()) {
                    Task task = parseTaskFromFileLine(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                }
            }
            fileScanner.close();
        } catch (IOException e) {
            throw new BuddyException("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    public void save(ArrayList<Task> tasks) throws BuddyException {
        try {
            // Ensure directory exists
            createDirectoryIfNeeded();

            // Write tasks to file
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            for (Task task : tasks) {
                writer.write(convertTaskToFileLine(task));
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            throw new BuddyException("Error saving tasks: " + e.getMessage());
        }
    }

    private void createDirectoryIfNeeded() throws IOException {
        File directory = new File(this.directory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private Task parseTaskFromFileLine(String line) throws BuddyException {
        try {
            String[] parts = line.split(" \\| ");
            if (parts.length < 3) {
                throw new BuddyException("Corrupted task data: " + line);
            }

            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            Task task;
            switch (type) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    if (parts.length < 4) {
                        throw new BuddyException("Corrupted deadline data: " + line);
                    }
                    task = new Deadline(description, parts[3]);
                    break;
                case "E":
                    if (parts.length < 5) {
                        throw new BuddyException("Corrupted event data: " + line);
                    }
                    task = new Event(description, parts[3], parts[4]);
                    break;
                default:
                    throw new BuddyException("Unknown task type: " + type);
            }

            if (isDone) {
                task.markAsDone();
            }
            return task;
        } catch (Exception e) {
            throw new BuddyException("Error parsing task data: " + line);
        }
    }

    private String convertTaskToFileLine(Task task) {
        StringBuilder sb = new StringBuilder();

        // Task type
        if (task instanceof Todo) {
            sb.append("T");
        } else if (task instanceof Deadline) {
            sb.append("D");
        } else if (task instanceof Event) {
            sb.append("E");
        }

        // Completion status
        sb.append(" | ").append(task.isDone() ? "1" : "0");

        // Description
        sb.append(" | ").append(task.getDescription());

        // Additional data based on task type
        if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            sb.append(" | ").append(deadline.getByForStorage());
        } else if (task instanceof Event) {
            Event event = (Event) task;
            sb.append(" | ").append(event.getFromForStorage());
            sb.append(" | ").append(event.getToForStorage());
        }

        return sb.toString();
    }
}