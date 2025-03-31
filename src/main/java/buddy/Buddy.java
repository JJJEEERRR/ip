package buddy;

import buddy.commands.Command;
import buddy.data.TaskList;
import buddy.storage.Storage;
import buddy.ui.Ui;
import buddy.util.BuddyException;
import buddy.util.Parser;

/**
 * Main class for the Buddy task management application.
 * This class serves as the entry point and coordinates the different components
 * of the application including the UI, storage, and task management.
 */
public class Buddy {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Initializes the Buddy application with the specified storage file path.
     * This constructor sets up the UI, storage system, and loads the task list from file.
     * If loading fails, it will start with an empty task list.
     *
     * @param filePath The path to the file where tasks will be stored.
     */
    public Buddy(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (BuddyException e) {
            ui.showLoadingError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main application loop.
     * Displays the welcome message and continuously processes user commands
     * until the user issues an exit command.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                //ui.showLine();
                Command command = Parser.parse(fullCommand);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            } catch (BuddyException e) {
                ui.showError(e.getMessage());
            } finally {
                //ui.showLine();
            }
        }
    }

    /**
     * The entry point of the application.
     * Creates a new Buddy instance and runs it.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Buddy("./data/buddy.txt").run();
    }
}


// All code comply with coding standard

// Already implemented find command

