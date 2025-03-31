package buddy.commands;

import buddy.data.TaskList;
import buddy.storage.Storage;
import buddy.ui.Ui;
import buddy.util.BuddyException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Command to display a random motivational quote for software engineers.
 */
public class CheerCommand extends Command {
    private static final String CHEER_FILE = "./data/cheer.txt";
    private static final Random RANDOM = new Random();

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BuddyException {
        try {
            List<String> quotes = loadQuotes();
            if (quotes.isEmpty()) {
                throw new BuddyException("No motivational quotes found. Please check " + CHEER_FILE);
            }

            String randomQuote = quotes.get(RANDOM.nextInt(quotes.size()));
            ui.showCheer(randomQuote);
        } catch (IOException e) {
            throw new BuddyException("Error loading motivational quotes: " + e.getMessage());
        }
    }

    /**
     * Loads motivational quotes from the quotes file.
     *
     * @return List of motivational quotes
     * @throws IOException If an error occurs reading the file
     */
    private List<String> loadQuotes() throws IOException {
        List<String> quotes = new ArrayList<>();
        File file = new File(CHEER_FILE);

        // If file doesn't exist, create directory and return empty list
        if (!file.exists()) {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                boolean dirCreated = parent.mkdirs();
                if (!dirCreated) {
                    throw new IOException("Failed to create directory: " + parent.getAbsolutePath());
                }
            }
            return quotes;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    quotes.add(line.trim());
                }
            }
        }

        return quotes;
    }
}