package vaughandroid.vigor.app.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.math.BigDecimal;

import vaughandroid.vigor.app.mvp.LCEView;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * MVP contract for {@link ExerciseActivity}
 *
 * @author Chris
 */
public interface ExerciseContract {

    interface View extends LCEView {
        void setWeight(@Nullable BigDecimal weight);
        void setWeightUnits(@NonNull String units);
        void setReps(@Nullable Integer reps);

        void onSaved();
    }

    interface Presenter extends vaughandroid.vigor.app.mvp.Presenter {
        void setView(@Nullable View view);
        void init(@NonNull WorkoutId workoutId, @NonNull ExerciseId exerciseId);

        void onWeightEntered(@Nullable BigDecimal weight);
        void onRepsEntered(@Nullable Integer reps);
        void onValuesConfirmed();
    }
}
