package logger;

import java.util.logging.Logger;

public class ServiceLogger {

    private static final Logger log = Logger.getLogger(ServiceLogger.class.getName());

    public Logger getLogger() {
        return log;
    }
}
