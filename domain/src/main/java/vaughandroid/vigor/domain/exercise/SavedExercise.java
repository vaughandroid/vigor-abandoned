package vaughandroid.vigor.domain.exercise;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

/**
 * An {@link Exercise} which has been saved.
 *
 * @author Chris
 */
@AutoValue
public abstract class SavedExercise implements Serializable {

    public static SavedExercise create(SavedExerciseId id, Exercise data) {
        return new AutoValue_SavedExercise(id, data);
    }

    public abstract SavedExerciseId id();

    public abstract Exercise exercise();
}
