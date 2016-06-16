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

        void openNewExerciseActivity();
        void openExistingExerciseActivity(@NonNull ExerciseId exerciseId);
    }

    interface Presenter extends vaughandroid.vigor.app.mvp.Presenter {
        void setView(@Nullable View view);
        void setWorkoutId(@Nullable WorkoutId workoutId);

        void onAddExercise();
    }
}
