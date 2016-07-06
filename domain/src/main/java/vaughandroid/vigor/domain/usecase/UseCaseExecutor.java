package vaughandroid.vigor.domain.usecase;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Applies {@link Scheduler}s to {@link UseCase}s, as well as managing a set of {@link rx.Subscription}s
 *
 * @author Chris
 */
public class UseCaseExecutor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Observable.Transformer<Object, Object> transformer;
    private final Action1<Throwable> onErrorDefault = t -> logger.error("", t);

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Inject
    public UseCaseExecutor(final @Named("subscription") Scheduler subscriptionScheduler,
            final @Named("observation") Scheduler observationScheduler) {
        transformer = objectObservable -> objectObservable
                .subscribeOn(subscriptionScheduler)
                .observeOn(observationScheduler);
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

    public <T> void subscribe(@NotNull UseCase<T> useCase) {
        compositeSubscription.add(useCase.createObservable()
                .compose(this.<T>getTransformer())
                .subscribe(o -> {}, onErrorDefault));
    }

    public <T> void subscribe(@NotNull UseCase<T> useCase, @NotNull Action1<T> onNext) {
        compositeSubscription.add(useCase.createObservable()
                .compose(this.<T>getTransformer())
                .subscribe(onNext, onErrorDefault));
    }

    public <T> void subscribe(@NotNull UseCase<T> useCase, @NotNull Action1<T> onNext,
            @NotNull Action1<Throwable> onError) {
        compositeSubscription.add(useCase.createObservable()
                .compose(this.<T>getTransformer())
                .subscribe(onNext, onError));
    }

    public <T> void subscribe(@NotNull UseCase<T> useCase, @NotNull Action1<T> onNext,
            @NotNull Action1<Throwable> onError, @NotNull Action0 onCompleted) {
        compositeSubscription.add(useCase.createObservable()
                .compose(this.<T>getTransformer())
                .subscribe(onNext, onError, onCompleted));
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
