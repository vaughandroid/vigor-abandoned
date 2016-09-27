package vaughandroid.vigor.data.firebase.database;

import com.google.common.base.Preconditions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import javax.inject.Inject;
import rx.Completable;
import rx.Observable;
import rx.Single;
import vaughandroid.vigor.app.di.ApplicationScope;

/**
 * Firebase database wrapper class.
 *
 * @author Chris
 */
@ApplicationScope
public class FirebaseDatabaseWrapperImpl implements FirebaseDatabaseWrapper {

  private FirebaseDatabase mDatabase;

  @Inject public FirebaseDatabaseWrapperImpl() {
  }

  @Override public void init() {
    mDatabase = FirebaseDatabase.getInstance();
    // Enable offline support
    mDatabase.setPersistenceEnabled(true);
  }

  @Override public Observable<Boolean> connectedStatus() {
    checkInitialised();
    return observe(".info/connected", Boolean.class);
  }

  @Override public Single<Boolean> dataExists(String path) {
    checkInitialised();
    // TODO: This also notifies when the child changes, so it's inefficient.
    // TODO: Should just create it as a single.
    return Observable.create(
        DataChangeOnSubscribe.create(mDatabase.getReference(path).limitToFirst(1),
            DataSnapshot::exists)).toSingle();
  }

  @Override public <T> Observable<T> observe(String path, Class<T> clazz) {
    checkInitialised();
    return Observable.create(DataChangeOnSubscribe.create(mDatabase.getReference(path),
        dataSnapshot -> dataSnapshot.getValue(clazz)));
  }

  @Override
  public <T> Observable<T> observe(String path, GenericTypeIndicator<T> genericTypeIndicator) {
    checkInitialised();
    return Observable.create(DataChangeOnSubscribe.create(mDatabase.getReference(path),
        dataSnapshot -> dataSnapshot.getValue(genericTypeIndicator)));
  }

  @Override public Completable set(String path, Object value) {
    checkInitialised();

    return Completable.create(completableSubscriber -> {
      mDatabase.getReference(path).setValue(value, (databaseError, databaseReference) -> {
        if (databaseError != null) {
          completableSubscriber.onError(new FirebaseDatabaseException(databaseError));
        } else {
          completableSubscriber.onCompleted();
        }
      });
    });
  }

  private void checkInitialised() {
    Preconditions.checkState(mDatabase != null, "not initialised!");
  }
}
