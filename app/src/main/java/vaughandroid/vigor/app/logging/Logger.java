package vaughandroid.vigor.app.logging;

/**
 * Basic interface for logging.
 *
 * @author Chris
 */
public interface Logger {

    void d(String message, Object... args);

    void e(String message, Object... args);
    void e(Throwable t, String message, Object... args);

}
