package vaughandroid.vigor.domain.usecase;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import rx.Observable;
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

    private final Observable.Transformer<Object, Object> scheduleTransformer;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Inject
    public UseCaseExecutor(ScheduleTransformer scheduleTransformer) {
        this.scheduleTransformer = scheduleTransformer;
    }

    public <T> void subscribe(@NotNull UseCase<T> useCase, @Nullable Subscriber<T> subscriber) {
        compositeSubscription.add(useCase.createObservable()
                .compose(this.<T>applySchedulers())
                .subscribe(subscriber));
    }

    public <T> void subscribe(@NotNull UseCase<T> useCase, @Nullable SingleSubscriber<T> subscriber) {
        compositeSubscription.add(useCase.createObservable()
                .compose(this.<T>applySchedulers())
                .toSingle()
                .subscribe(subscriber));
    }

    // TODO: find a way to hide this in the ScheduleTransformer
    @SuppressWarnings("unchecked")
    private <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) scheduleTransformer;
    }

    public void unsubscribeAll() {
        compositeSubscription.unsubscribe();
        // Need a new CompositeSubscription instance if we are to add new subscriptions.
        compositeSubscription = new CompositeSubscription();
    }

}
