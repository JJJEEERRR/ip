package buddy.commands;

import java.time.LocalDateTime;
import buddy.data.TaskList;
import buddy.storage.Storage;
import buddy.ui.Ui;
import buddy.util.BuddyException;
import buddy.data.Task;

public class EventCommand extends Command {
    private String description;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    public EventCommand(String description, LocalDateTime fromDate, LocalDateTime toDate) {
        super();
        this.description = description;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BuddyException {
        Task eventTask = tasks.addEvent(description, fromDate, toDate);
        ui.showAddedTask(eventTask, tasks.size());
        storage.save(tasks.getTasks());
    }
}