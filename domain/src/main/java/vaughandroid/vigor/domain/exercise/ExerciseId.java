package vaughandroid.vigor.domain.exercise;

import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.NotNull;

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

    @NotNull
    public abstract String guid();
}
