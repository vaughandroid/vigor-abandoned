package vaughandroid.vigor.domain.rx;

import rx.Observable;
import rx.Scheduler;

/**
 * Helper for applying {@link rx.Scheduler}s to {@link rx.Observable}s.
 *
 * @author chris.vaughan@laterooms.com
 */
public class SchedulingPolicy {

    private final Observable.Transformer<Object, Object> transformer;

    public SchedulingPolicy(Scheduler subscriptionScheduler, Scheduler observationScheduler) {
        transformer = new Observable.Transformer<Object, Object>() {
            @Override
            public Observable<Object> call(Observable<Object> objectObservable) {
                return objectObservable
                        .subscribeOn(subscriptionScheduler)
                        .observeOn(observationScheduler);
            }
        };
    }

    /**
     * Needs to be called with the annoying {@code instance.<T>apply()} syntax.
     *
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> Observable.Transformer<T, T> apply() {
        return (Observable.Transformer<T, T>) transformer;
    }
}
