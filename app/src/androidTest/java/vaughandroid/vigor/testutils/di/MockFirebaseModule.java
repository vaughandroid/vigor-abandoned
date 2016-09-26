package vaughandroid.vigor.testutils.di;

import dagger.Module;
import dagger.Provides;
import org.mockito.Mockito;
import rx.Completable;
import vaughandroid.vigor.app.di.ApplicationScope;
import vaughandroid.vigor.data.firebase.database.FirebaseDatabaseWrapper;
import vaughandroid.vigor.data.firebase.database.FirebaseDatabaseWrapperImpl;
import vaughandroid.vigor.testutils.StubFirebaseDatabaseWrapper;

/**
 * Module which provides mock Firebase classes for injection.
 *
 * @author chris.vaughan@laterooms.com
 */
@Module
public class MockFirebaseModule {

  // TODO: 26/09/2016 May be better off writing a custom stub for this, but it isn't trivial.
  @Provides @ApplicationScope FirebaseDatabaseWrapper provideFirebaseDatabaseWrapper() {
    return new StubFirebaseDatabaseWrapper();
  }
}
