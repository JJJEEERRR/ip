package buddy.util;

import buddy.commands.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    @Test
    public void testParseExitCommand() throws BuddyException {
        Command command = Parser.parse("bye");
        assertTrue(command instanceof ExitCommand);
        assertTrue(command.isExit());
    }

    @Test
    public void testParseListCommand() throws BuddyException {
        Command command = Parser.parse("list");
        assertTrue(command instanceof ListCommand);
        assertFalse(command.isExit());
    }

    @Test
    public void testParseTodoCommand() throws BuddyException {
        Command command = Parser.parse("todo Read a book");
        assertTrue(command instanceof TodoCommand);

        // We would need to access the description field to test it
        // This could be done through reflection or by adding a getter
    }

    @Test
    public void testParseDeadlineCommand() throws BuddyException {
        Command command = Parser.parse("deadline Submit report /by 15/4/2025 1400");
        assertTrue(command instanceof DeadlineCommand);
    }

    @Test
    public void testParseEventCommand() throws BuddyException {
        Command command = Parser.parse("event Team meeting /from 15/4/2025 1400 /to 15/4/2025 1600");
        assertTrue(command instanceof EventCommand);
    }

    @Test
    public void testParseInvalidCommand() {
        Exception exception = assertThrows(BuddyException.class, () -> {
            Parser.parse("invalid command");
        });
        assertTrue(exception.getMessage().contains("Unknown command"));
    }

    @Test
    public void testParseEmptyTodoCommand() {
        Exception exception = assertThrows(BuddyException.class, () -> {
            Parser.parse("todo ");
        });
        assertTrue(exception.getMessage().contains("cannot be empty"));
    }

    @Test
    public void testParseInvalidDeadlineFormat() {
        Exception exception = assertThrows(BuddyException.class, () -> {
            Parser.parse("deadline Submit report");
        });
        assertTrue(exception.getMessage().contains("Invalid deadline format"));
    }
}