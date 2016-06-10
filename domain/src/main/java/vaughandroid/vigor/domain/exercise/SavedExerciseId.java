package vaughandroid.vigor.domain.exercise;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

/**
 * ID for an {@link SavedExercise}
 *
 * @author Chris
 */
@AutoValue
public abstract class SavedExerciseId implements Serializable {

    public static SavedExerciseId create(int value) {
        return new AutoValue_SavedExerciseId(value);
    }

    public abstract int value();
}
