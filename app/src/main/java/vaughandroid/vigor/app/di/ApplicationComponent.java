package vaughandroid.vigor.app.di;

import dagger.Component;
import vaughandroid.vigor.app.rx.SchedulerModule;
import vaughandroid.vigor.app.rx.UtilsModule;
import vaughandroid.vigor.data.firebase.database.FirebaseModule;

/**
 * Injector of {@link ApplicationScope}d instances.
 *
 * @author Chris
 */
@Component(modules = {
        FirebaseModule.class,
        SchedulerModule.class,
        UtilsModule.class,
})
@ApplicationScope
public interface ApplicationComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);
}
