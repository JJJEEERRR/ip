package buddy.commands;

import java.time.LocalDateTime;
import buddy.data.TaskList;
import buddy.storage.Storage;
import buddy.ui.Ui;
import buddy.util.BuddyException;
import buddy.data.Task;

public class MarkCommand extends Command {
    private int taskIndex;

    public MarkCommand(int taskIndex) {
        super();
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BuddyException {
        Task markedTask = tasks.markTaskAsDone(taskIndex);
        ui.showMarkedTask(markedTask);
        storage.save(tasks.getTasks());
    }
}