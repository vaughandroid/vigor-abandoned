package vaughandroid.vigor.data.firebase.database;

import com.google.firebase.database.GenericTypeIndicator;
import rx.Completable;
import rx.Observable;
import rx.Single;

/**
 * TODO: javadoc
 *
 * @author Chris
 */
public interface FirebaseDatabaseWrapper {
  void init();

  Observable<Boolean> connectedStatus();

  Single<Boolean> dataExists(String path);

  <T> Observable<T> observe(String path, Class<T> clazz);

  <T> Observable<T> observe(String path, GenericTypeIndicator<T> genericTypeIndicator);

  Completable set(String path, Object value);
}
