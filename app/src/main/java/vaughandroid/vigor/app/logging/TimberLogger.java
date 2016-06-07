package vaughandroid.vigor.app.logging;

import javax.inject.Inject;

import timber.log.Timber;
import vaughandroid.vigor.app.di.ApplicationScope;

/**
 * {@link Logger} implementation built on Timber.
 *
 * @author Chris
 */
@ApplicationScope
public class TimberLogger implements Logger {

    @Inject
    public TimberLogger() {}

    @Override
    public void d(String message, Object... args) {
        Timber.d(message);
    }

    @Override
    public void e(String message, Object... args) {
        Timber.e(message, args);
    }

    @Override
    public void e(Throwable t, String message, Object... args) {
        Timber.e(t, message);
    }
}
