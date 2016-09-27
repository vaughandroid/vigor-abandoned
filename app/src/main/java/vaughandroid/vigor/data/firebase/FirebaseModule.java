package vaughandroid.vigor.data.firebase;

import dagger.Module;
import dagger.Provides;
import vaughandroid.vigor.app.di.ApplicationScope;
import vaughandroid.vigor.data.firebase.database.FirebaseDatabaseWrapper;
import vaughandroid.vigor.data.firebase.database.FirebaseDatabaseWrapperImpl;

/**
 * Module which provides Firebase classes for injection.
 *
 * @author Chris
 */
@Module public class FirebaseModule {

  @Provides @ApplicationScope FirebaseDatabaseWrapper provideFirebaseDatabaseWrapper(
      FirebaseDatabaseWrapperImpl impl) {
    return impl;
  }
}
