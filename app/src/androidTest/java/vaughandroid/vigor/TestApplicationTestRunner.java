package vaughandroid.vigor;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

/**
 * Custom {@link AndroidJUnitRunner} which launches {@link TestApplication}
 *
 * @author chris.vaughan@laterooms.com
 */
public class TestApplicationTestRunner extends AndroidJUnitRunner {

  @Override public Application newApplication(ClassLoader cl, String className, Context context)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return super.newApplication(cl, TestApplication.class.getName(), context);
  }
}
