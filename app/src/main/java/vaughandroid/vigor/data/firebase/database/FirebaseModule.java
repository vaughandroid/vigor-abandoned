package vaughandroid.vigor.data.firebase.database;

import com.google.firebase.database.FirebaseDatabase;

import dagger.Module;
import dagger.Provides;
import vaughandroid.vigor.app.di.ApplicationScope;

/**
 * @author Chris
 */
@Module
public class FirebaseModule {

    @Provides @ApplicationScope
    public FirebaseDatabase provideFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }
}
