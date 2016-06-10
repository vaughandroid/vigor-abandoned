package vaughandroid.vigor.domain.usecase;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;

/**
 * Applies {@link Scheduler}s to {@link UseCase}s
 *
 * @author Chris
 */
public class UseCaseExecutor {

    private final Scheduler subscriptionScheduler;
    private final Scheduler observationScheduler;

    @Inject
    public UseCaseExecutor(@Named("subscription") Scheduler subscriptionScheduler,
            @Named("observation") Scheduler observationScheduler) {
        this.subscriptionScheduler = subscriptionScheduler;
        this.observationScheduler = observationScheduler;
    }

    public <T> Subscription subscribe(@NotNull UseCase<T> useCase, @Nullable Subscriber<T> subscriber) {
        return subscribe(useCase.createObservable(), subscriber);
    }

    public <T> Subscription subscribe(@NotNull Observable<T> observable, @Nullable Subscriber<T> subscriber) {
        return observable
                .subscribeOn(subscriptionScheduler)
                .observeOn(observationScheduler)
                .subscribe(subscriber);
    }
}
