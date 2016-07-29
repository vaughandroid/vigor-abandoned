package vaughandroid.vigor.domain.exercise.type;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ComparisonChain;

import java.io.Serializable;
import java.util.Comparator;

/**
 * A type of exercise.
 *
 * @author Chris
 */
@AutoValue
public abstract class ExerciseType implements Serializable {

    public static final ExerciseType UNSET = create(ExerciseTypeId.UNASSIGNED, "");

    public static ExerciseType create(@NonNull String name) {
        return create(ExerciseTypeId.UNASSIGNED, name);
    }

    public static ExerciseType create(@NonNull ExerciseTypeId id, @NonNull String name) {
        return builder()
                .id(id)
                .name(name)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_ExerciseType.Builder()
                .id(ExerciseTypeId.UNASSIGNED);
    }

    @NonNull
    public abstract ExerciseTypeId id();
    public abstract ExerciseType withId(@NonNull ExerciseTypeId id);

    @NonNull
    public String guid() {
        return id().guid();
    }

    @NonNull
    public abstract String name();
    public String nameLowercase() {
        return name().toLowerCase();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder id(ExerciseTypeId id);
        public abstract Builder name(String name);

        public abstract ExerciseType build();
    }

    /**
     * Orders {@link ExerciseType}s by {@link ExerciseType#name()}
     */
    public static class NameComparator implements Comparator<ExerciseType> {

        @Override
        public int compare(ExerciseType o1, ExerciseType o2) {
            return ComparisonChain.start()
                    .compare(o1.name(), o2.name())
                    .result();
        }
    }
}
