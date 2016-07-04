package vaughandroid.vigor.domain.usecase;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Applies {@link Scheduler}s to {@link UseCase}s, as well as managing a set of {@link rx.Subscription}s
 *
 * @author Chris
 */
public class UseCaseExecutor {

    private final Observable.Transformer<Object, Object> transformer;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Inject
    public UseCaseExecutor(final @Named("subscription") Scheduler subscriptionScheduler,
            final @Named("observation") Scheduler observationScheduler) {
        transformer = new Observable.Transformer<Object, Object>() {
            @Override
            public Observable<Object> call(Observable<Object> objectObservable) {
                return objectObservable
                        .subscribeOn(subscriptionScheduler)
                        .observeOn(observationScheduler);
            }
        };
    }

    public <T> void subscribe(@NotNull UseCase<T> useCase, @NotNull Subscriber<T> subscriber) {
        compositeSubscription.add(useCase.createObservable()
                .compose(this.<T>getTransformer())
                .subscribe(subscriber));
    }

    public <T> void subscribe(@NotNull UseCase<T> useCase, @NotNull SingleSubscriber<T> subscriber) {
        compositeSubscription.add(useCase.createObservable()
                .compose(this.<T>getTransformer())
                .toSingle()
                .subscribe(subscriber));
    }

    public void unsubscribe() {
        compositeSubscription.unsubscribe();
        // Need a new CompositeSubscription instance if we are to add new subscriptions.
        compositeSubscription = new CompositeSubscription();
    }

    @SuppressWarnings("unchecked")
    private <T> Observable.Transformer<T, T> getTransformer() {
        return (Observable.Transformer<T, T>) transformer;
    }
}
