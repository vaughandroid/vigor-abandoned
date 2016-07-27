package vaughandroid.vigor.data.firebase.database;

import com.google.common.base.Preconditions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.Subscriptions;

/**
 * {@link rx.Observable.OnSubscribe} which wraps {@link DatabaseReference} value changed events.
 *
 * @author Chris
 */
public class ValueEventOnSubscribe<T> implements Observable.OnSubscribe<T> {

    public static <T> ValueEventOnSubscribe<T> forClass(DatabaseReference databaseReference, Class<T> clazz) {
        return new ValueEventOnSubscribe<T>(databaseReference, clazz, null);
    }

    public static <T> ValueEventOnSubscribe<T> forGenericTypeIndicator(DatabaseReference databaseReference,
            GenericTypeIndicator<T> genericTypeIndicator) {
        return new ValueEventOnSubscribe<T>(databaseReference, null, genericTypeIndicator);
    }

    private final DatabaseReference databaseReference;
    private final Class<T> clazz;
    private final GenericTypeIndicator<T> genericTypeIndicator;

    private ValueEventOnSubscribe(DatabaseReference databaseReference, Class<T> clazz,
            GenericTypeIndicator<T> genericTypeIndicator) {
        Preconditions.checkState(clazz != null || genericTypeIndicator != null,
                "must provide either a class or a generic type indicator");
        this.databaseReference = databaseReference;
        this.clazz = clazz;
        this.genericTypeIndicator = genericTypeIndicator;
    }

    @Override
    public void call(final Subscriber<? super T> subscriber) {
        final ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (clazz != null) {
                    subscriber.onNext(dataSnapshot.getValue(clazz));
                } else {
                    subscriber.onNext(dataSnapshot.getValue(genericTypeIndicator));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                subscriber.onError(new FirebaseDatabaseException(databaseError));
            }
        };
        databaseReference.addValueEventListener(listener);

        subscriber.add(Subscriptions.create(() -> databaseReference.removeEventListener(listener)));
    }
}
