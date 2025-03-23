package buddy.commands;
import buddy.data.TaskList;
import buddy.storage.Storage;
import buddy.ui.Ui;
import buddy.util.BuddyException;
import buddy.data.Task;

public class DeleteCommand extends Command {
    private int taskIndex;

    public DeleteCommand(int taskIndex) {
        super();
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BuddyException {
        Task deletedTask = tasks.deleteTask(taskIndex);
        ui.showDeletedTask(deletedTask, tasks.size());
        storage.save(tasks.getTasks());
    }
}