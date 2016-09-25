package vaughandroid.vigor.app.workout;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.List;
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
    void setExercises(@NonNull List<Exercise> exercises);

    void goToAddNewExercise(@NonNull WorkoutId workoutId);

    void goToEditExistingExercise(@NonNull WorkoutId workoutId, @NonNull ExerciseId exerciseId);
  }

  interface Presenter {
    void init(@NonNull View view, @NonNull WorkoutId workoutId);

    void onAddExercise();

    void onOpenExercise(@NonNull Exercise exercise);

    void onError(Throwable t);
  }
}
