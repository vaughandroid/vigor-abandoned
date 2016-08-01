package vaughandroid.vigor.data.firebase.database;

import com.google.common.base.Preconditions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import javax.inject.Inject;

import rx.Observable;
import rx.Single;
import rx.subjects.PublishSubject;
import vaughandroid.vigor.app.di.ApplicationScope;

/**
 * Firebase database wrapper class.
 *
 * @author Chris
 */
@ApplicationScope
public class FirebaseDatabaseWrapper {

    private FirebaseDatabase mDatabase;

    @Inject
    public FirebaseDatabaseWrapper() {
    }

    public void init() {
        mDatabase = FirebaseDatabase.getInstance();
        // Enable offline support
        mDatabase.setPersistenceEnabled(true);
    }

    public Observable<Boolean> connectedStatus() {
        checkInitialised();
        return observe(".info/connected", Boolean.class);
    }

    public Single<Boolean> dataExists(String path) {
        checkInitialised();
        // TODO: This also notifies when the child changes, so it's inefficient.
        // TODO: More efficient to create it as a single.
        return Observable.create(DataChangeOnSubscribe.create(mDatabase.getReference(path).limitToFirst(1),
                DataSnapshot::exists))
                .toSingle();
    }

    public <T> Observable<T> observe(String path, Class<T> clazz) {
        checkInitialised();
        return Observable.create(DataChangeOnSubscribe.create(mDatabase.getReference(path),
                dataSnapshot -> dataSnapshot.getValue(clazz)));
    }

    public <T> Observable<T> observe(String path, GenericTypeIndicator<T> genericTypeIndicator) {
        checkInitialised();
        return Observable.create(DataChangeOnSubscribe.create(mDatabase.getReference(path),
                dataSnapshot -> dataSnapshot.getValue(genericTypeIndicator)));
    }

    public Observable<Void> set(String path, Object value) {
        checkInitialised();
        PublishSubject<Void> subject = PublishSubject.create();
        mDatabase.getReference(path)
                .setValue(value, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        subject.onError(new FirebaseDatabaseException(databaseError));
                    } else {
                        subject.onCompleted();
                    }
                });
        return subject.asObservable();
    }

    private void checkInitialised() {
        Preconditions.checkState(mDatabase != null, "not initialised!");
    }
}
