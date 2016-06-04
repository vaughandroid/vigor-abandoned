package vaughandroid.vigor.domain.usecase;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;

/**
 * Applies {@link Scheduler}s to {@link UseCase}s
 *
 * @author Chris
 */
public class UseCaseExecutor {

    private final Scheduler subscriptionScheduler;
    private final Scheduler observationScheduler;

    @Inject
    public UseCaseExecutor(Scheduler subscriptionScheduler, Scheduler observationScheduler) {
        this.subscriptionScheduler = subscriptionScheduler;
        this.observationScheduler = observationScheduler;
    }

    public <T> Observable<T> schedule(UseCase<T> useCase) {
        return useCase.createObservable()
                .subscribeOn(subscriptionScheduler)
                .observeOn(observationScheduler);
    }
}
