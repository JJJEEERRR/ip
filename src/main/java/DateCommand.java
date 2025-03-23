import java.time.LocalDateTime;
import java.util.ArrayList;

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