package vaughandroid.vigor.testutils;

import com.google.firebase.database.GenericTypeIndicator;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import rx.Completable;
import rx.Observable;
import rx.Single;
import vaughandroid.vigor.data.firebase.database.FirebaseDatabaseWrapper;

/**
 * Sub implementation of {@link FirebaseDatabaseWrapper}
 *
 * @author chris.vaughan@laterooms.com
 */
public class StubFirebaseDatabaseWrapper implements FirebaseDatabaseWrapper {

  public boolean connected;
  public Map<String, Object> data = new HashMap<>();

  @Override public void init() {

  }

  @Override public Observable<Boolean> connectedStatus() {
    return Observable.just(connected);
  }

  @Override public Single<Boolean> dataExists(String path) {
    return Single.just(data.containsKey(path));
  }

  @Override public <T> Observable<T> observe(String path, Class<T> clazz) {
    return Observable.just(clazz.cast(data.get(path)));
  }

  @Override
  public <T> Observable<T> observe(String path, GenericTypeIndicator<T> genericTypeIndicator) {
    // Nasty reflection here!
    ParameterizedType t = (ParameterizedType) genericTypeIndicator.getClass().getGenericSuperclass();
    Class<T> clazz = (Class<T>) t.getActualTypeArguments()[0]; // XXX this is crashing
    return observe(path, clazz);
  }

  @Override public Completable set(String path, Object value) {
    data.put(path, value);
    return Completable.complete();
  }
}
