package vaughandroid.vigor.app.di;

import dagger.Component;
import vaughandroid.vigor.app.logging.LoggingModule;

/**
 * Injector of {@link ApplicationScope}d instances.
 *
 * @author Chris
 */
@Component(modules = {
        LoggingModule.class,
})
@ApplicationScope
public interface ApplicationComponent {

    ActivityComponent activityComponent();
}
