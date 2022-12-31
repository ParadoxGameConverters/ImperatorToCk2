package ImperatorToCK2;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConverterLogger {
    static private FileHandler file;
    static private ConsoleHandler console;

    static public void setup() throws IOException {

        // get the global logger to configure it
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setUseParentHandlers(false);

        logger.setLevel(Level.INFO);
        file = new FileHandler("log.txt");
        console = new ConsoleHandler();

        // create a TXT formatter
        ConverterLogFormatter fileFormatter = new ConverterLogFormatter();
        file.setFormatter(fileFormatter);
        logger.addHandler(file);

        ConverterLogFormatter consoleFormatter = new ConverterLogFormatter();
        console.setFormatter(consoleFormatter);
        logger.addHandler(console);
    }
}
