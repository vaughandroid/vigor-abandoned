package vaughandroid.vigor.domain.exercise.type;

import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import vaughandroid.vigor.domain.exercise.type.AutoValue_ExerciseTypeId;

/**
 * TODO: javadoc
 *
 * @author Chris
 */
@AutoValue
public abstract class ExerciseTypeId implements Serializable {

    public static final ExerciseTypeId UNASSIGNED = create("unassigned");

    public static ExerciseTypeId create(String guid) {
        return new AutoValue_ExerciseTypeId(guid);
    }

    @NotNull
    public abstract String guid();
}
