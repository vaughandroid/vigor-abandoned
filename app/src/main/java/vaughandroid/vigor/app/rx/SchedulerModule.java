package vaughandroid.vigor.app.rx;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import vaughandroid.vigor.app.di.ApplicationScope;

/**
 * Provides {@link Scheduler}s for injection.
 *
 * @author Chris
 */
@Module
public class SchedulerModule {

    @Provides @Named("subscription")
    @ApplicationScope
    public Scheduler provideSubscriptionScheduler() {
        return Schedulers.io();
    }

    @Provides @Named("observation")
    @ApplicationScope
    public Scheduler provideObservationScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
