package vaughandroid.vigor.domain.usecase;

import rx.Observable;

/**
 * Base interface for all use cases.
 *
 * @author Chris
 */
public interface UseCase<T> {

    Observable<T> createObservable();
}
