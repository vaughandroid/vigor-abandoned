package vaughandroid.vigor.domain.exercise.type;

import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * TODO: javadoc
 *
 * @author Chris
 */
@AutoValue
public abstract class ExerciseType implements Serializable {

    public static final ExerciseType UNSET = create(ExerciseTypeId.UNASSIGNED, "");

    public static ExerciseType create(ExerciseTypeId id, String name) {
        return new AutoValue_ExerciseType(id, name);
    }

    @NotNull
    public abstract ExerciseTypeId id();

    @NotNull
    public abstract String name();
}
