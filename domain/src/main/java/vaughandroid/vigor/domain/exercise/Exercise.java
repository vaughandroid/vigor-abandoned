package vaughandroid.vigor.domain.exercise;

import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Data for an exercise.
 *
 * @author Chris
 */
@AutoValue
public abstract class Exercise implements Serializable {

    public static Builder builder() {
        return new AutoValue_Exercise.Builder()
                .id(null)
                .weight(BigDecimal.ZERO);
    }

    @Nullable
    public abstract ExerciseId id();
    public abstract Exercise withId(@Nullable ExerciseId id);

    public abstract int reps();
    public abstract Exercise withReps(int reps);

    public abstract BigDecimal weight();
    public abstract Exercise withWeight(@NotNull BigDecimal weight);

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(@Nullable ExerciseId id);
        public abstract Builder reps(int reps);
        public abstract Builder weight(@NotNull BigDecimal weight);
        public abstract Exercise build();
    }
}
