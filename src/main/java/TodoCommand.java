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