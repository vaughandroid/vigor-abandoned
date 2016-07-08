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
        transformer = objectObservable -> objectObservable
                .subscribeOn(subscriptionScheduler)
                .observeOn(observationScheduler);
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
