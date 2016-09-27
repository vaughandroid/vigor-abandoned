package vaughandroid.vigor.app.workout;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import org.junit.Rule;
import org.junit.Test;
import vaughandroid.vigor.TestApplication;

/**
 * Tests for {@link WorkoutActivity}
 *
 * @author Chris
 */
public class WorkoutActivityTests {

  @Rule public IntentsTestRule<WorkoutActivity> activityRule =
      new IntentsTestRule<>(WorkoutActivity.class, true, false);

  final WorkoutActivityRobot robot =
      new WorkoutActivityRobot(TestApplication.testApplicationComponent);

  @Test public void clicking_fab_adds_new_exercise() throws Exception {
    activityRule.launchActivity(null);

    robot.setup().expectExerciseIntent();

    robot.perform().clickFAB();

    robot.check()
        .receivedNewExerciseIntent()
        .noUnverifiedIntents();
  }
}
