package vaughandroid.vigor.data.firebase.database.rx;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.Subscriptions;
import vaughandroid.vigor.data.firebase.database.FirebaseDatabaseException;

/**
 * {@link rx.Observable.OnSubscribe} which wraps {@link DatabaseReference} value changed events.
 *
 * @author Chris
 */
public class ValueEventOnSubscribe<T> implements Observable.OnSubscribe<T> {

    private final DatabaseReference databaseReference;
    private final Class<T> clazz;

    public ValueEventOnSubscribe(DatabaseReference databaseReference, Class<T> clazz) {
        this.databaseReference = databaseReference;
        this.clazz = clazz;
    }

    @Override
    public void call(final Subscriber<? super T> subscriber) {
        final ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                subscriber.onNext(dataSnapshot.getValue(clazz));
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
