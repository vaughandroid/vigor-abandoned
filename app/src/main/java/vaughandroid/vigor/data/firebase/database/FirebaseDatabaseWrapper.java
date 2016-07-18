package vaughandroid.vigor.data.firebase.database;

import com.google.common.base.Preconditions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import rx.Observable;
import vaughandroid.vigor.data.firebase.database.rx.RxFirebase;

/**
 * Firebase database wrapper class.
 *
 * @author Chris
 */
public class FirebaseDatabaseWrapper {

    private FirebaseDatabase mDatabase;

    public void init() {
        mDatabase = FirebaseDatabase.getInstance();
        // Enable offline support
        mDatabase.setPersistenceEnabled(true);
    }

    public Observable<Boolean> connectedStatus() {
        Preconditions.checkState(mDatabase != null, "not initialised!");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(".info/connected");
        return RxFirebase.observe(ref, Boolean.class);
    }
}
