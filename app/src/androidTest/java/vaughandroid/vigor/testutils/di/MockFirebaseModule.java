package vaughandroid.vigor.testutils.di;

import dagger.Module;
import dagger.Provides;
import vaughandroid.vigor.app.di.ApplicationScope;
import vaughandroid.vigor.data.firebase.database.FirebaseDatabaseWrapper;
import vaughandroid.vigor.testutils.StubFirebaseDatabaseWrapper;

/**
 * Module which provides mock Firebase classes for injection.
 *
 * @author Chris
 */
@Module public class MockFirebaseModule {

  @Provides @ApplicationScope FirebaseDatabaseWrapper provideFirebaseDatabaseWrapper(
      StubFirebaseDatabaseWrapper impl) {
    return impl;
  }

  @Provides @ApplicationScope StubFirebaseDatabaseWrapper provideStubFirebaseDatabaseWrapper() {
    return new StubFirebaseDatabaseWrapper();
  }
}
