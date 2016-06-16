package vaughandroid.vigor.app.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.math.BigDecimal;

import vaughandroid.vigor.app.mvp.LCEView;
import vaughandroid.vigor.domain.exercise.ExerciseId;

/**
 * MVP contract for {@link ExerciseActivity}
 *
 * @author Chris
 */
public interface ExerciseContract {

    interface View extends LCEView {
        void setWeight(@NonNull BigDecimal weight);
        void setWeightUnits(@NonNull String units);
        void setReps(int reps);

        void onSaved();
    }

    interface Presenter extends vaughandroid.vigor.app.mvp.Presenter {
        void setView(@Nullable View view);
        void setExerciseId(@Nullable ExerciseId exerciseId);

        void onWeightIncremented();
        void onWeightDecremented();
        void onWeightEntered(@NonNull String weight);

        void onRepsIncremented();
        void onRepsDecremented();
        void onRepsEntered(@NonNull String reps);

        void onValuesConfirmed();
    }
}
