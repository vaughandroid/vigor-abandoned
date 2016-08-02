package vaughandroid.vigor.app.di;

import android.app.Activity;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import dagger.Module;
import dagger.Provides;
import vaughandroid.vigor.app.VigorActivity;

/**
 * @author Chris
 */
@Module public class ActivityModule {

  private final VigorActivity vigorActivity;

  public ActivityModule(VigorActivity vigorActivity) {
    this.vigorActivity = vigorActivity;
  }

  @Provides @ActivityScope public Activity provideActivity() {
    return vigorActivity;
  }

  @Provides @ActivityScope public ActivityLifecycleProvider provideActivityLifecycleProvider() {
    return vigorActivity;
  }
}
