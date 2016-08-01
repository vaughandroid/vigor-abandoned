package vaughandroid.vigor.domain.rx;

import rx.Observable;
import rx.Scheduler;
import rx.Single;

/**
 * Helper for applying {@link rx.Scheduler}s to {@link rx.Observable}s.
 *
 * @author Chris
 */
public class SchedulingPolicy {

  private final Observable.Transformer<Object, Object> observableTransformer;
  private final Single.Transformer<Object, Object> singleTransformer;

  public SchedulingPolicy(Scheduler subscriptionScheduler, Scheduler observationScheduler) {
    observableTransformer = objectObservable -> objectObservable.subscribeOn(subscriptionScheduler)
        .observeOn(observationScheduler);
    singleTransformer = objectSingle -> objectSingle.subscribeOn(subscriptionScheduler)
        .observeOn(observationScheduler);
  }

  /**
   * Needs to be called with the annoying {@code instance.<T>apply()} syntax.
   */
  @SuppressWarnings("unchecked") public <T> Observable.Transformer<T, T> observableTransformer() {
    return (Observable.Transformer<T, T>) observableTransformer;
  }

  /**
   * Needs to be called with the annoying {@code instance.<T>apply()} syntax.
   */
  @SuppressWarnings("unchecked") public <T> Single.Transformer<T, T> singleTransformer() {
    return (Single.Transformer<T, T>) singleTransformer;
  }
}
