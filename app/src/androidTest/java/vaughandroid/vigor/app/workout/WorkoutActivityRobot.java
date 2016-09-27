package vaughandroid.vigor.app.workout;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.matcher.ViewMatchers;
import javax.inject.Inject;
import vaughandroid.vigor.R;
import vaughandroid.vigor.app.exercise.ExerciseActivity;
import vaughandroid.vigor.data.firebase.database.FirebaseDatabaseWrapper;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.testutils.di.TestApplicationComponent;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.assertNoUnverifiedIntents;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Test robot for {@link WorkoutActivity}
 *
 * @author Chris
 */
public class WorkoutActivityRobot {

  public WorkoutActivityRobot(TestApplicationComponent testApplicationComponent) {
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

    public Setup expectExerciseIntent() {
      intending(hasComponent(ExerciseActivity.class.getName()))
          .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
      return this;
    }
  }

  public class Perform {

    public Perform clickFAB() {
      onView(ViewMatchers.withId(R.id.fab)).perform(click());
      return this;
    }
  }

  public class Check {

    public Check receivedNewExerciseIntent() {
      intended(allOf(
          hasComponent(ExerciseActivity.class.getName()),
          hasExtraWithKey(ExerciseActivity.EXTRA_WORKOUT_ID),
          hasExtra(ExerciseActivity.EXTRA_EXERCISE_ID, ExerciseId.UNASSIGNED)));
      return this;
    }

    public Check noUnverifiedIntents() {
      assertNoUnverifiedIntents();
      return this;
    }
  }
}
