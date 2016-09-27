package vaughandroid.vigor.app.exercise;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.ViewMatchers;
import com.google.common.collect.ImmutableList;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.assertj.core.api.Assertions;
import vaughandroid.vigor.R;
import vaughandroid.vigor.app.exercise.type.ExerciseTypePickerActivity;
import vaughandroid.vigor.data.exercise.ExerciseDto;
import vaughandroid.vigor.data.exercise.ExerciseMapper;
import vaughandroid.vigor.data.exercise.type.ExerciseTypeMapper;
import vaughandroid.vigor.data.workout.WorkoutMapper;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.exercise.type.ExerciseTypeId;
import vaughandroid.vigor.domain.exercise.type.InitExerciseTypesUseCase;
import vaughandroid.vigor.domain.workout.Workout;
import vaughandroid.vigor.testutils.StubFirebaseDatabaseWrapper;
import vaughandroid.vigor.testutils.di.TestApplicationComponent;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.assertNoUnverifiedIntents;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Test robot for {@link ExerciseActivity}
 *
 * @author chris.vaughan@laterooms.com
 */
public class ExerciseActivityRobot {

  private static Map<ExerciseTypeId, ExerciseType> exerciseTypeMap = new HashMap<>();

  static {
    List<ExerciseType> list = ImmutableList.of(
        ExerciseType.create(ExerciseTypeId.create("1"), "Squat"),
        ExerciseType.create(ExerciseTypeId.create("2"), "Deadlift"),
        ExerciseType.create(ExerciseTypeId.create("3"), "Bench Press"),
        ExerciseType.create(ExerciseTypeId.create("4"), "Overhead Press"),
        ExerciseType.create(ExerciseTypeId.create("5"), "Bent-over Row"));
    for (ExerciseType type : list) {
      exerciseTypeMap.put(type.id(), type);
    }
  }

  @Inject StubFirebaseDatabaseWrapper stubFirebaseDatabaseWrapper;
  @Inject ExerciseTypeMapper exerciseTypeMapper;
  @Inject ExerciseMapper exerciseMapper;
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
      for (ExerciseType type : exerciseTypeMap.values()) {
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
      onView(withId(R.id.content_exercise_TextView_type)).perform(click());
      return this;
    }

    public Perform enterWeight(@NonNull String value) {
      onView(allOf(
          withId(R.id.view_number_input_EditText_value),
          isDescendantOfA(withId(R.id.content_exercise_NumberInputView_weight))
      )).perform(typeText(value));
      return this;
    }

    public Perform enterReps(int value) {
      onView(allOf(
          withId(R.id.view_number_input_EditText_value),
          isDescendantOfA(withId(R.id.content_exercise_NumberInputView_reps))
      )).perform(typeText(Integer.toString(value)));
      return this;
    }

    public Perform clickDone() {
      onView(withId(R.id.content_exercise_Button_confirm)).perform(click());
      return this;
    }
  }

  public class Check {

    public Check savedWeight(String value) {
      Exercise exercise = getSavedExercise();
      assertThat(exercise).isNotNull();
      assertThat(exercise.weight()).isEqualByComparingTo(new BigDecimal(value));
      return this;
    }

    public Check savedReps(int value) {
      return this;
    }

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

    private Exercise getSavedExercise() {
      for (Object o : stubFirebaseDatabaseWrapper.data.values()) {
        if (o instanceof ExerciseDto) {
          return exerciseMapper.fromDto((ExerciseDto) o, exerciseTypeMap);
        }
      }
      return null;
    }
  }
}
