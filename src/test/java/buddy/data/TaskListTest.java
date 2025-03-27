package buddy.data;

import buddy.util.BuddyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }

    @Test
    public void testAddTodo() {
        Task task = taskList.addTodo("Read a book");
        assertEquals(1, taskList.size());
        assertEquals("Read a book", task.getDescription());
        assertFalse(task.isDone());
        assertTrue(task instanceof Todo);
    }

    @Test
    public void testMarkTaskAsDone() throws BuddyException {
        taskList.addTodo("Read a book");
        Task task = taskList.markTaskAsDone(0);
        assertTrue(task.isDone());
    }

    @Test
    public void testMarkTaskAsUndone() throws BuddyException {
        taskList.addTodo("Read a book");
        taskList.markTaskAsDone(0);
        Task task = taskList.markTaskAsUndone(0);
        assertFalse(task.isDone());
    }

    @Test
    public void testDeleteTask() throws BuddyException {
        taskList.addTodo("Task 1");
        taskList.addTodo("Task 2");
        Task deletedTask = taskList.deleteTask(0);
        assertEquals(1, taskList.size());
        assertEquals("Task 1", deletedTask.getDescription());
    }

    @Test
    public void testDeleteTaskWithInvalidIndex() {
        taskList.addTodo("Task 1");
        Exception exception = assertThrows(BuddyException.class, () -> {
            taskList.deleteTask(1);
        });
        assertTrue(exception.getMessage().contains("Task index out of range"));
    }

    @Test
    public void testFindTasks() {
        taskList.addTodo("Read Java book");
        taskList.addTodo("Complete Java assignment");
        taskList.addTodo("Watch movie");

        ArrayList<Task> foundTasks = taskList.findTasks("Java");
        assertEquals(2, foundTasks.size());
    }

    @Test
    public void testGetTasksOnDate() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tomorrow = today.plusDays(1);

        taskList.addDeadline("Submit report", today);
        taskList.addEvent("Team meeting", today, today.plusHours(2));
        taskList.addDeadline("Submit homework", tomorrow);

        ArrayList<Task> tasksToday = taskList.getTasksOnDate(today);
        assertEquals(2, tasksToday.size());
    }
}