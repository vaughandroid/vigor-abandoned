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

        void openNewExerciseActivity(@NonNull WorkoutId workoutId);
        void openExistingExerciseActivity(@NonNull WorkoutId workoutId, @NonNull ExerciseId exerciseId);
    }

    interface Presenter extends vaughandroid.vigor.app.mvp.Presenter {
        void setView(@Nullable View view);
        void setWorkoutId(@NonNull WorkoutId workoutId);

        void onAddExercise();
        void onOpenExercise(@NonNull Exercise exercise);
    }
}
