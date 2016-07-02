package vaughandroid.vigor.domain.usecase;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Scheduler;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Applies {@link Scheduler}s to {@link UseCase}s
 *
 * @author Chris
 */
public class UseCaseExecutor {

    private final Scheduler subscriptionScheduler;
    private final Scheduler observationScheduler;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Inject
    public UseCaseExecutor(@Named("subscription") Scheduler subscriptionScheduler,
            @Named("observation") Scheduler observationScheduler) {
        this.subscriptionScheduler = subscriptionScheduler;
        this.observationScheduler = observationScheduler;
    }

    public <T> void subscribe(@NotNull UseCase<T> useCase, @Nullable Subscriber<T> subscriber) {
        compositeSubscription.add(useCase.createObservable()
                .subscribeOn(subscriptionScheduler)
                .observeOn(observationScheduler)
                .subscribe(subscriber));
    }

    public <T> void subscribe(@NotNull UseCase<T> useCase, @Nullable SingleSubscriber<T> subscriber) {
        compositeSubscription.add(useCase.createObservable()
                .subscribeOn(subscriptionScheduler)
                .observeOn(observationScheduler)
                .toSingle()
                .subscribe(subscriber));
    }

    public void unsubscribeAll() {
        compositeSubscription.unsubscribe();
        // Need a new CompositeSubscription instance if we are to add new subscriptions.
        compositeSubscription = new CompositeSubscription();
    }
}
