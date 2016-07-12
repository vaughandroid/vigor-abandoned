package vaughandroid.vigor.domain.rx;

import rx.Subscriber;

/**
 * No-op {@link Subscriber}, to be used as a base when we are only interested in one or two event types.
 *
 * @author Chris
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {
        // No op
    }

    @Override
    public void onError(Throwable e) {
        // No op
    }

    @Override
    public void onNext(T t) {
        // No op
    }
}
