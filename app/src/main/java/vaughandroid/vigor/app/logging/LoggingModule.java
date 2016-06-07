package vaughandroid.vigor.app.logging;

import dagger.Module;
import dagger.Provides;
import vaughandroid.vigor.app.di.ApplicationScope;

/**
 * Provides logging-related classes for injection.
 *
 * @author Chris
 */
@Module
public class LoggingModule {

    @Provides
    @ApplicationScope
    public Logger provideLogger(TimberLogger logger) {
        return logger;
    }
}
