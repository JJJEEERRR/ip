package buddy.commands;

import buddy.data.Task;
import buddy.data.TaskList;
import buddy.storage.Storage;
import buddy.ui.Ui;
import buddy.util.BuddyException;

public class UnmarkCommand extends Command {
    private int taskIndex;

    public UnmarkCommand(int taskIndex) {
        super();
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BuddyException {
        Task unmarkedTask = tasks.markTaskAsUndone(taskIndex);
        ui.showUnmarkedTask(unmarkedTask);
        storage.save(tasks.getTasks());
    }
}