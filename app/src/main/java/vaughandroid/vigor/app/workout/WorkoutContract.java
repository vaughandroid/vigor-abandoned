package vaughandroid.vigor.app.workout;

import android.support.annotation.NonNull;
import com.google.common.collect.ImmutableList;
import vaughandroid.vigor.app.mvp.LCEView;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * MVP contract for {@link WorkoutActivity}
 *
 * @author Chris
 */
public interface WorkoutContract {

  interface View extends LCEView {
    void setExercises(@NonNull ImmutableList<Exercise> exercises);

    void goToAddNewExercise(@NonNull WorkoutId workoutId);

    void goToEditExistingExercise(@NonNull WorkoutId workoutId, @NonNull ExerciseId exerciseId);
  }

  interface Presenter extends vaughandroid.vigor.app.mvp.Presenter<View> {
    void setWorkoutId(@NonNull WorkoutId workoutId);

    void onAddExercise();

    void onOpenExercise(@NonNull Exercise exercise);

    void onExerciseAdded(@NonNull Exercise exercise);

    void onExerciseUpdated(@NonNull Exercise exercise);
  }
}
