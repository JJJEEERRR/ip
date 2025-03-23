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