package vaughandroid.vigor.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import vaughandroid.vigor.app.di.ActivityComponent;
import vaughandroid.vigor.app.di.ActivityComponentSource;
import vaughandroid.vigor.app.di.ActivityModule;
import vaughandroid.vigor.app.di.ApplicationComponent;
import vaughandroid.vigor.app.di.ApplicationComponentSource;

/**
 * Custom {@link Activity} base class.
 *
 * @author Chris
 */
public abstract class VigorActivity extends RxAppCompatActivity
    implements ApplicationComponentSource, ActivityComponentSource {

  private ActivityComponent activityComponent;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityComponent = getApplicationComponent().activityComponent(new ActivityModule(this));
  }

  @Override public ApplicationComponent getApplicationComponent() {
    return ((ApplicationComponentSource) getApplication()).getApplicationComponent();
  }

  @Override public ActivityComponent getActivityComponent() {
    return activityComponent;
  }
}
