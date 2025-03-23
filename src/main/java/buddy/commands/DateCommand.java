package buddy.commands;

import java.time.LocalDateTime;
import java.util.ArrayList;
import buddy.data.TaskList;
import buddy.storage.Storage;
import buddy.ui.Ui;
import buddy.util.BuddyException;
import buddy.data.Task;

public class DateCommand extends Command {
    private LocalDateTime searchDate;

    public DateCommand(LocalDateTime searchDate) {
        super();
        this.searchDate = searchDate;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> tasksOnDate = tasks.getTasksOnDate(searchDate);
        ui.showTasksOnDate(tasksOnDate, searchDate);
    }
}