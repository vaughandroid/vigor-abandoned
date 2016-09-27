package vaughandroid.vigor.app.exercise;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import vaughandroid.vigor.TestApplication;
import vaughandroid.vigor.domain.workout.Workout;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * Tests for {@link ExerciseActivity}
 *
 * @author chris.vaughan@laterooms.com
 */
public class ExerciseActivityTests {

  @Rule public IntentsTestRule<ExerciseActivity> activityRule =
      new IntentsTestRule<>(ExerciseActivity.class, true, false);

  private final ExerciseActivityRobot robot =
      new ExerciseActivityRobot(TestApplication.testApplicationComponent);

  private final Workout workout = Workout.builder().id(WorkoutId.create("1234")).build();

  @Before public void setup() {
    robot.setup()
        .initExerciseTypes()
        .addWorkout(workout);
  }

  @Test public void clicking_exercise_type_opens_type_picker() throws Exception {
    launchForNewExercise();
    robot.setup()
        .expectExerciseTypePickerIntent();

    robot.perform().clickExerciseType();

    robot.check()
        .receivedNewExerciseTypePickerIntent()
        .noUnverifiedIntents();
  }

  private void launchForNewExercise() {
    activityRule.launchActivity(
        ExerciseActivity.intentForNew(InstrumentationRegistry.getTargetContext(), workout.id()));
  }
}
