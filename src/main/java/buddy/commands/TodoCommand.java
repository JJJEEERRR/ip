package buddy.commands;

import buddy.data.Task;
import buddy.data.TaskList;
import buddy.storage.Storage;
import buddy.ui.Ui;
import buddy.util.BuddyException;

public class TodoCommand extends Command {
    private String description;

    public TodoCommand(String description) {
        super();
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BuddyException {
        Task todoTask = tasks.addTodo(description);
        ui.showAddedTask(todoTask, tasks.size());
        storage.save(tasks.getTasks());
    }
}