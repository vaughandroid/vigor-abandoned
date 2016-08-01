package vaughandroid.vigor.data.firebase.database;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseError;

/**
 * {@link RuntimeException} wrapping a Firebase {@link DatabaseError}
 *
 * @author Chris
 */
public class FirebaseDatabaseException extends RuntimeException {

    private final DatabaseError databaseError;

    public FirebaseDatabaseException(@NonNull DatabaseError databaseError) {
        this(databaseError, null);
    }

    public FirebaseDatabaseException(@NonNull DatabaseError databaseError, String message) {
        super(message);
        this.databaseError = databaseError;
    }

    public DatabaseError databaseError() {
        return databaseError;
    }
}
