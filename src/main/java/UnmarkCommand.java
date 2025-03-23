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