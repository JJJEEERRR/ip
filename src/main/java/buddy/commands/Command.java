package buddy.commands;

import buddy.data.TaskList;
import buddy.storage.Storage;
import buddy.ui.Ui;
import buddy.util.BuddyException;

public abstract class Command {
    protected boolean isExit;

    public Command() {
        this.isExit = false;
    }

    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws BuddyException;

    public boolean isExit() {
        return isExit;
    }
}