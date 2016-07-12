package vaughandroid.vigor.domain.exercise;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

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
