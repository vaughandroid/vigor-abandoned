package vaughandroid.vigor.app.exercise;

import android.support.annotation.NonNull;

import java.math.BigDecimal;

import vaughandroid.vigor.domain.exercise.SavedExercise;

/**
 * MVP contract for {@link ExerciseActivity}
 *
 * @author Chris
 */
public interface ExerciseContract {

    interface View {
        void setWeight(@NonNull BigDecimal weight);
        void setWeightUnits(@NonNull String units);
        void setReps(int reps);

        void finish(SavedExercise savedExercise);
    }

    interface Presenter extends vaughandroid.vigor.app.mvp.Presenter {
        void setView(View view);

        void onWeightChanged(@NonNull BigDecimal weight);
        void onRepsChanged(int reps);
        void onValuesConfirmed();
    }
}
