package vaughandroid.vigor.app.di;

import dagger.Component;
import vaughandroid.vigor.app.rx.SchedulerModule;

/**
 * Injector of {@link ApplicationScope}d instances.
 *
 * @author Chris
 */
@Component(modules = {
        SchedulerModule.class
})
@ApplicationScope
public interface ApplicationComponent {

    ActivityComponent activityComponent();
}
