package vaughandroid.vigor;

import android.app.Application;
import vaughandroid.vigor.app.VigorApplication;
import vaughandroid.vigor.testutils.di.DaggerTestApplicationComponent;
import vaughandroid.vigor.testutils.di.TestApplicationComponent;

/**
 * Custom {@link Application} class for running tests.
 *
 * @author Chris
 */
public class TestApplication extends VigorApplication {

  public static TestApplicationComponent testApplicationComponent;

  @Override public TestApplicationComponent getApplicationComponent() {
    if (testApplicationComponent == null) {
      testApplicationComponent = DaggerTestApplicationComponent.create();
    }
    return testApplicationComponent;
  }
}
