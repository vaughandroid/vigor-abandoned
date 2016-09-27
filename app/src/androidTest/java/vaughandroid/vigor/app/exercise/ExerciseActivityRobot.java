package vaughandroid.vigor.app.exercise;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.ViewMatchers;
import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.inject.Inject;
import vaughandroid.vigor.R;
import vaughandroid.vigor.app.exercise.type.ExerciseTypePickerActivity;
import vaughandroid.vigor.data.exercise.type.ExerciseTypeMapper;
import vaughandroid.vigor.data.workout.WorkoutMapper;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.exercise.type.ExerciseTypeId;
import vaughandroid.vigor.domain.exercise.type.InitExerciseTypesUseCase;
import vaughandroid.vigor.domain.workout.Workout;
import vaughandroid.vigor.testutils.StubFirebaseDatabaseWrapper;
import vaughandroid.vigor.testutils.di.TestApplicationComponent;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.assertNoUnverifiedIntents;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Test robot for {@link ExerciseActivity}
 *
 * @author chris.vaughan@laterooms.com
 */
public class ExerciseActivityRobot {

  @Inject StubFirebaseDatabaseWrapper stubFirebaseDatabaseWrapper;
  @Inject ExerciseTypeMapper exerciseTypeMapper;
  @Inject WorkoutMapper workoutMapper;

  public ExerciseActivityRobot(TestApplicationComponent testApplicationComponent) {
    testApplicationComponent.inject(this);
  }

  public Setup setup() {
    return new Setup();
  }

  public Perform perform() {
    return new Perform();
  }

  public Check check() {
    return new Check();
  }

  public class Setup {

    public Setup expectExerciseTypePickerIntent() {
      intending(hasComponent(ExerciseTypePickerActivity.class.getName()))
          .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
      return this;
    }

    // TODO: This is one-off application init, so probably belongs elsewhere
    public Setup initExerciseTypes() {
      List<ExerciseType> types = ImmutableList.of(
          ExerciseType.create(ExerciseTypeId.create("1"), "Squat"),
          ExerciseType.create(ExerciseTypeId.create("2"), "Deadlift"),
          ExerciseType.create(ExerciseTypeId.create("3"), "Bench Press"),
          ExerciseType.create(ExerciseTypeId.create("4"), "Overhead Press"),
          ExerciseType.create(ExerciseTypeId.create("5"), "Bent-over Row"));
      for (ExerciseType type : types) {
        stubFirebaseDatabaseWrapper.data.put(
            "exerciseTypes/" + type.id().guid(),
            exerciseTypeMapper.fromExerciseType(type));
      }
      return this;
    }

    public Setup addWorkout(@NonNull Workout workout) {
      // TODO: Maybe expose the path creation methods in the Repositories
      stubFirebaseDatabaseWrapper.data.put(
          "workouts/" + workout.id().guid(),
          workoutMapper.fromWorkout(workout));
      return this;
    }
  }

  public class Perform {

    public Perform clickExerciseType() {
      onView(ViewMatchers.withId(R.id.content_exercise_TextView_type)).perform(click());
      return this;
    }
  }

  public class Check {

    public Check receivedNewExerciseTypePickerIntent() {
      intended(allOf(
          hasComponent(ExerciseTypePickerActivity.class.getName()),
          hasExtraWithKey(ExerciseTypePickerActivity.EXTRA_EXERCISE_ID)));
      return this;
    }

    public Check noUnverifiedIntents() {
      assertNoUnverifiedIntents();
      return this;
    }
  }
}
