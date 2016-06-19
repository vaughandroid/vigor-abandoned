package vaughandroid.vigor.domain.exercise;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

/**
 * ID for an {@link Exercise}
 *
 * @author Chris
 */
@AutoValue
public abstract class ExerciseId implements Serializable {

    public static ExerciseId create(String guid) {
        return new AutoValue_ExerciseId(guid);
    }

    public abstract String guid();
}