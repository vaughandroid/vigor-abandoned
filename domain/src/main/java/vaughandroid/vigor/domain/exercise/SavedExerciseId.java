package vaughandroid.vigor.domain.exercise;

import com.google.auto.value.AutoValue;

/**
 * ID for an {@link SavedExercise}
 *
 * @author chris.vaughan@laterooms.com
 */
@AutoValue
public abstract class SavedExerciseId {

    public static SavedExerciseId create(int value) {
        return new AutoValue_SavedExerciseId(value);
    }

    public abstract int value();
}
