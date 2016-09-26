package vaughandroid.vigor.testutils.di;

import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import vaughandroid.vigor.app.di.ApplicationScope;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;

/**
 * Provides {@link Scheduler}s for use when running tests.
 *
 * @author Chris
 */
@Module public class TestSchedulerModule {

  @Provides @Named("subscription") @ApplicationScope
  public Scheduler provideSubscriptionScheduler() {
    return Schedulers.immediate();
  }

  @Provides @Named("observation") @ApplicationScope public Scheduler provideObservationScheduler() {
    return AndroidSchedulers.mainThread();
  }

  @Provides @ApplicationScope public SchedulingPolicy provideSchedulingPolicy(
      @Named("subscription") Scheduler subscriptionScheduler,
      @Named("observation") Scheduler observationScheduler) {
    return new SchedulingPolicy(subscriptionScheduler, observationScheduler);
  }
}
