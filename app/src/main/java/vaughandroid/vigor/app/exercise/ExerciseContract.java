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

  interface View {
    void setType(@NonNull ExerciseType type);

    void setWeight(@Nullable BigDecimal weight, @NonNull String units);

    void setReps(@Nullable Integer reps);

    void showError();

    void goToExerciseTypePicker(@NonNull ExerciseType type);

    void onSaved(@NonNull Exercise exercise);
  }

  interface Presenter extends vaughandroid.vigor.app.mvp.Presenter<View> {
    void init(@NonNull WorkoutId workoutId, @NonNull ExerciseId exerciseId);

    void onTypeClicked();

    void onValuesConfirmed(@Nullable BigDecimal weight, @Nullable Integer reps);

    void onTypePicked(@NonNull ExerciseType typeFromResult);
  }
}
