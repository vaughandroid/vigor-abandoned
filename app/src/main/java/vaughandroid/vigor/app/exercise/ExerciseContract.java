package vaughandroid.vigor.app.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.math.BigDecimal;
import vaughandroid.vigor.app.mvp.LCEView;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * MVP contract for {@link ExerciseActivity}
 *
 * @author Chris
 */
public interface ExerciseContract {

  interface View extends LCEView {
    void setType(@NonNull ExerciseType type);

    void setWeight(@Nullable BigDecimal weight);

    void setWeightUnits(@NonNull String units);

    void setReps(@Nullable Integer reps);

    void openTypePicker(@NonNull ExerciseType type);

    void onSaved(@NonNull Exercise exercise);
  }

  interface Presenter extends vaughandroid.vigor.app.mvp.Presenter<View> {
    void init(@NonNull WorkoutId workoutId, @NonNull ExerciseId exerciseId);

    void onTypeClicked();

    void onWeightEntered(@Nullable BigDecimal weight);

    void onRepsEntered(@Nullable Integer reps);

    void onValuesConfirmed();

    void onTypePicked(@NonNull ExerciseType typeFromResult);
  }
}
