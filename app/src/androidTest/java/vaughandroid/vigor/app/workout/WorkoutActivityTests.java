package vaughandroid.vigor.app.workout;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import vaughandroid.vigor.TestApplication;

/**
 * Tests for {@link WorkoutActivity}
 *
 * @author chris.vaughan@laterooms.com
 */
public class WorkoutActivityTests {

  @Rule public IntentsTestRule<WorkoutActivity> activityRule =
      new IntentsTestRule<>(WorkoutActivity.class, true, false);

  WorkoutActivityRobot robot;

  @Before public void setup() {
    robot = new WorkoutActivityRobot(((TestApplication) InstrumentationRegistry.getTargetContext()
        .getApplicationContext()).getApplicationComponent());
  }

  @Test public void clicking_fab_adds_new_exercise() throws Exception {
    activityRule.launchActivity(null);

    robot.setup().expectExerciseIntent();

    robot.perform().clickFAB();

    robot.check()
        .receivedNewExerciseIntent()
        .noUnverifiedIntents();
  }
}
