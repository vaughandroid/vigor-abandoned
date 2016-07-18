package vaughandroid.vigor.data.firebase.database.rx;

import com.google.firebase.database.DatabaseReference;

import rx.Observable;

/**
 * RX Firebase factory methods.
 *
 * @author Chris
 */
public class RxFirebase {

    public static <T> Observable<T> observe(DatabaseReference databaseReference, Class<T> clazz) {
        return Observable.create(new ValueEventOnSubscribe<T>(databaseReference, clazz));
    }

    private RxFirebase() {
        throw new AssertionError("no instances!");
    }
}
