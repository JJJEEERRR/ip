package buddy.commands;

import java.time.LocalDateTime;
import buddy.data.TaskList;
import buddy.storage.Storage;
import buddy.ui.Ui;
import buddy.util.BuddyException;
import buddy.data.Task;

public class DeadlineCommand extends Command {
    private String description;
    private LocalDateTime byDate;

    public DeadlineCommand(String description, LocalDateTime byDate) {
        super();
        this.description = description;
        this.byDate = byDate;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BuddyException {
        Task deadlineTask = tasks.addDeadline(description, byDate);
        ui.showAddedTask(deadlineTask, tasks.size());
        storage.save(tasks.getTasks());
    }
}