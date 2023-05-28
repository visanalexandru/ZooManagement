package zoo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class is a singleton class that contains methods used to log actions to
 * a csv file.
 */
public class Logger {
    /**
     * The instance of the logger.
     */
    private static Logger logger = null;

    /**
     * The file writer.
     */
    private final FileWriter writer;

    /**
     * The date formatter.
     */
    private final DateTimeFormatter formatter;

    private Logger() throws IOException {
        formatter = DateTimeFormatter.ofPattern("yyyy:MMM:dd HH:mm:ss");

        File f = new File("history.csv");
        if (!f.exists()) {
            writer = new FileWriter(f);
            writer.write("action,timestamp\n");
        } else {
            writer = new FileWriter(f, true);
        }

    }

    /**
     * Logs the given message to the log file.
     *
     * @param message the message to be logged to the file.
     */
    public void logMessage(String message) {
        try {
            LocalDateTime now = LocalDateTime.now();
            String date = formatter.format(now);
            writer.write(date + "," + message + '\n');
            writer.flush();
        } catch (IOException exception) {
            System.out.println("Cannot write to the log file: " + exception.getMessage());
        }
    }

    public static Logger getLogger() {
        if (logger == null) {
            try {
                logger = new Logger();
            } catch (IOException exception) {
                System.out.println("Cannot initialize the logger: " + exception.getMessage());
                logger = null;
            }
        }
        return logger;
    }
}
