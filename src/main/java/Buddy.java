import java.util.ArrayList;

public class Buddy {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    public Buddy(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();
        try {
            tasks = new TaskList(storage.load());
        } catch (BuddyException e) {
            ui.showLoadingError(e.getMessage());
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isRunning = true;

        while (isRunning) {
            String input = ui.readCommand();
            try {
                Command command = parser.parseCommand(input);
                isRunning = executeCommand(command);
                if (command.shouldSave()) {
                    storage.save(tasks.getTasks());
                }
            } catch (BuddyException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    private boolean executeCommand(Command command) throws BuddyException {
        switch (command.getType()) {
            case EXIT:
                ui.showGoodbye();
                return false;
            case LIST:
                ui.showTaskList(tasks.getTasks());
                break;
            case MARK:
                Task markedTask = tasks.markTaskAsDone(command.getTaskIndex());
                ui.showMarkedTask(markedTask);
                break;
            case UNMARK:
                Task unmarkedTask = tasks.markTaskAsUndone(command.getTaskIndex());
                ui.showUnmarkedTask(unmarkedTask);
                break;
            case TODO:
                Task todoTask = tasks.addTodo(command.getDescription());
                ui.showAddedTask(todoTask, tasks.size());
                break;
            case DEADLINE:
                Task deadlineTask = tasks.addDeadline(command.getDescription(), command.getByDate());
                ui.showAddedTask(deadlineTask, tasks.size());
                break;
            case EVENT:
                Task eventTask = tasks.addEvent(command.getDescription(), command.getFromDate(), command.getToDate());
                ui.showAddedTask(eventTask, tasks.size());
                break;
            case DELETE:
                Task deletedTask = tasks.deleteTask(command.getTaskIndex());
                ui.showDeletedTask(deletedTask, tasks.size());
                break;
            case FIND:
                ArrayList<Task> foundTasks = tasks.findTasks(command.getDescription());
                ui.showFoundTasks(foundTasks);
                break;
            case DATE:
                ArrayList<Task> tasksOnDate = tasks.getTasksOnDate(command.getSearchDate());
                ui.showTasksOnDate(tasksOnDate, command.getSearchDate());
                break;
            default:
                throw new BuddyException("Unknown command type");
        }
        return true;
    }

    public static void main(String[] args) {
        new Buddy("./data/buddy.txt").run();
    }
}