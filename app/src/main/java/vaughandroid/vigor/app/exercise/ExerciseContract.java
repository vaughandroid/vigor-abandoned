package vaughandroid.vigor.app.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.math.BigDecimal;
import vaughandroid.vigor.app.mvp.LCEView;
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

    void setWeight(@Nullable BigDecimal weight, @NonNull String units);

    void setReps(@Nullable Integer reps);

    void goToExerciseTypePicker(@NonNull ExerciseId exerciseId);

    void finish();
  }

  interface Presenter {
    void init(@NonNull View view, @NonNull WorkoutId workoutId, @NonNull ExerciseId exerciseId);

    void onWeightChanged(@Nullable BigDecimal weight);

    void onRepsChanged(@Nullable Integer reps);

    void onTypeClicked();

    void onValuesConfirmed();

    void onBack();

    void onError(Throwable t);
  }
}
