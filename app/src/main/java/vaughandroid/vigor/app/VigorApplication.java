package vaughandroid.vigor.app;

import android.app.Application;
import javax.inject.Inject;
import vaughandroid.vigor.app.di.ApplicationComponent;
import vaughandroid.vigor.app.di.ApplicationComponentSource;
import vaughandroid.vigor.app.di.DaggerApplicationComponent;
import vaughandroid.vigor.data.firebase.database.FirebaseDatabaseWrapper;

/**
 * Custom {@link Application} class.
 *
 * @author Chris
 */
public class VigorApplication extends Application implements ApplicationComponentSource {

  private ApplicationComponent applicationComponent;

  @Override public void onCreate() {
    super.onCreate();

    getApplicationComponent().inject(this);
  }

  @Override public ApplicationComponent getApplicationComponent() {
    if (applicationComponent == null) {
      applicationComponent = DaggerApplicationComponent.create();
    }
    return applicationComponent;
  }

  @Inject void inject(FirebaseDatabaseWrapper firebaseDatabaseWrapper) {
    firebaseDatabaseWrapper.init();
  }
}
