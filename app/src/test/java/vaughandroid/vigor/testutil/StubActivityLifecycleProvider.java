package vaughandroid.vigor.testutil;

import android.support.annotation.NonNull;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import rx.Completable;
import rx.Observable;
import rx.Single;

/**
 * Stub implementation of {@link ActivityLifecycleProvider} for unit tests.
 *
 * @author chris.vaughan@laterooms.com
 */
public class StubActivityLifecycleProvider implements ActivityLifecycleProvider {

  @NonNull @Override public Observable<ActivityEvent> lifecycle() {
    return Observable.never();
  }

  @NonNull @Override
  public <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
    return new NoOpLifecycleTransformer<>();
  }

  @NonNull @Override public <T> LifecycleTransformer<T> bindToLifecycle() {
    return new NoOpLifecycleTransformer<>();
  }

  private static class NoOpLifecycleTransformer<T> implements LifecycleTransformer<T> {

    @NonNull @Override public Single.Transformer<T, T> forSingle() {
      return single -> single;
    }

    @NonNull @Override public Completable.CompletableTransformer forCompletable() {
      return completable -> completable;
    }

    @Override public Observable<T> call(Observable<T> observable) {
      return observable;
    }
  }
}
