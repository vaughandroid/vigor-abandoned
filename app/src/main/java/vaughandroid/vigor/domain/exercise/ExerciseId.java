package vaughandroid.vigor.domain.exercise;

import com.google.auto.value.AutoValue;

import android.support.annotation.NonNull;

import java.io.Serializable;

import vaughandroid.vigor.domain.exercise.AutoValue_ExerciseId;

/**
 * ID for an {@link Exercise}
 *
 * @author Chris
 */
@AutoValue
public abstract class ExerciseId implements Serializable {

    public static final ExerciseId UNASSIGNED = create("unassigned");

    public static ExerciseId create(String guid) {
        return new AutoValue_ExerciseId(guid);
    }

    @NonNull
    public abstract String guid();
}
