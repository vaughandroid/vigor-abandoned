package vaughandroid.vigor.data.firebase.database;

import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

/**
 * TODO: javadoc
 *
 * @author Chris
 */
public class DataChangeOnSubscribe<T> implements Observable.OnSubscribe<T> {

  private final Query query;
  private final Func1<DataSnapshot, T> onSnapshotReceived;
  private DataChangeOnSubscribe(Query query, Func1<DataSnapshot, T> onSnapshotReceived) {
    this.query = query;
    this.onSnapshotReceived = onSnapshotReceived;
  }

  public static <T> DataChangeOnSubscribe<T> create(@NonNull Query query,
      @NonNull Func1<DataSnapshot, T> onSnapshotReceived) {
    return new DataChangeOnSubscribe<>(query, onSnapshotReceived);
  }

  @Override public void call(Subscriber<? super T> subscriber) {
    final ValueEventListener listener = new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        subscriber.onNext(onSnapshotReceived.call(dataSnapshot));
      }

      @Override public void onCancelled(DatabaseError databaseError) {
        subscriber.onError(new FirebaseDatabaseException(databaseError));
      }
    };
    query.addValueEventListener(listener);

    subscriber.add(Subscriptions.create(() -> query.removeEventListener(listener)));
  }
}
