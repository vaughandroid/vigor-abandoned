package vaughandroid.vigor.domain.usecase;

import rx.Observable;
import rx.Scheduler;

/**
 * {@link rx.Observable.Transformer} which employs subscription & observation {@link Scheduler} s.
 *
 * @author chris.vaughan@laterooms.com
 */
public class ScheduleTransformer implements Observable.Transformer<Object, Object> {

    private final Scheduler subscriptionScheduler;
    private final Scheduler observationScheduler;

    public ScheduleTransformer(Scheduler subscriptionScheduler, Scheduler observationScheduler) {
        this.subscriptionScheduler = subscriptionScheduler;
        this.observationScheduler = observationScheduler;
    }

    @Override
    public Observable<Object> call(Observable<Object> observable) {
        return observable
                .subscribeOn(subscriptionScheduler)
                .observeOn(observationScheduler);
    }
}
