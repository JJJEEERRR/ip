package buddy.commands;

import buddy.data.TaskList;
import buddy.storage.Storage;
import buddy.ui.Ui;
import buddy.util.BuddyException;

/**
 * Abstract base class for all commands in the Buddy application.
 * Defines the structure and common behavior for commands that
 * can be executed on the task list.
 */
public abstract class Command {
    protected boolean isExit;

    /**
     * Initializes a new command that does not exit the application.
     */
    public Command() {
        this.isExit = false;
    }

    /**
     * Executes this command using the given task list, UI, and storage.
     * This method must be implemented by all command subclasses.
     *
     * @param tasks The task list to operate on.
     * @param ui The UI to interact with the user.
     * @param storage The storage to save any changes to.
     * @throws BuddyException If there is an error during command execution.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws BuddyException;

    /**
     * Checks if this command should cause the application to exit.
     *
     * @return true if the application should exit after this command, false otherwise.
     */
    public boolean isExit() {
        return isExit;
    }
}