package vaughandroid.vigor.app.di;

import dagger.Component;
import vaughandroid.vigor.app.VigorApplication;
import vaughandroid.vigor.app.rx.SchedulerModule;
import vaughandroid.vigor.app.rx.UtilsModule;

/**
 * Injector of {@link ApplicationScope}d instances.
 *
 * @author Chris
 */
@Component(modules = {
    SchedulerModule.class, UtilsModule.class,
}) @ApplicationScope public interface ApplicationComponent {

  ActivityComponent activityComponent(ActivityModule activityModule);

  void inject(VigorApplication application);
}
