package vaughandroid.vigor.data.firebase.database;

import com.google.common.base.Preconditions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import javax.inject.Inject;

import rx.Observable;
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
    public FirebaseDatabaseWrapper() {}

    public void init() {
        mDatabase = FirebaseDatabase.getInstance();
        // Enable offline support
        mDatabase.setPersistenceEnabled(true);
    }

    public Observable<Boolean> connectedStatus() {
        checkInitialised();
        DatabaseReference ref = mDatabase.getReference(".info/connected");
        return Observable.create(ValueEventOnSubscribe.forClass(ref, Boolean.class));
    }

    public <T> Observable<T> observe(String path, Class<T> clazz) {
        checkInitialised();
        return Observable.create(ValueEventOnSubscribe.forClass(mDatabase.getReference(path), clazz));
    }

    public <T> Observable<T> observe(String path, GenericTypeIndicator<T> genericTypeIndicator) {
        checkInitialised();
        return Observable.create(ValueEventOnSubscribe.forGenericTypeIndicator(mDatabase.getReference(path), genericTypeIndicator));
    }

    public Observable<Void> set(String path, Object value) {
        PublishSubject<Void> subject = PublishSubject.create();
        mDatabase.getReference(path).setValue(value, (databaseError, databaseReference) -> {
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
