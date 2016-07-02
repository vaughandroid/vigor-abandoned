package vaughandroid.vigor.app.rx;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import vaughandroid.vigor.app.di.ApplicationScope;
import vaughandroid.vigor.domain.usecase.ScheduleTransformer;

/**
 * Provides {@link Scheduler}s for injection.
 *
 * @author Chris
 */
@Module
public class SchedulerModule {

    @Provides
    @ApplicationScope
    public ScheduleTransformer provideScheduleTransformer() {
        return new ScheduleTransformer(AndroidSchedulers.mainThread(), Schedulers.io());
    }
}
