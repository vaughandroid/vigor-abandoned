package vaughandroid.vigor.domain.exercise;

import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.math.BigDecimal;

import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * Data for an exercise.
 *
 * @author Chris
 */
@AutoValue
public abstract class Exercise implements Serializable {

    public static Builder builder() {
        return new AutoValue_Exercise.Builder()
                .id(ExerciseId.UNASSIGNED)
                .type(ExerciseType.UNSET);
    }

    @NotNull
    public abstract ExerciseId id();
    public abstract Exercise withId(@NotNull ExerciseId id);

    @NotNull
    public abstract WorkoutId workoutId();

    @NotNull
    public abstract ExerciseType type();
    public abstract Exercise withType(@NotNull ExerciseType type);

    @Nullable
    public abstract Integer reps();
    public abstract Exercise withReps(@Nullable Integer reps);

    @Nullable
    public abstract BigDecimal weight();
    public abstract Exercise withWeight(@Nullable BigDecimal weight);

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(@NotNull ExerciseId id);
        public abstract Builder type(@NotNull ExerciseType type);
        public abstract Builder workoutId(@NotNull WorkoutId workoutId);
        public abstract Builder reps(@Nullable Integer reps);
        public abstract Builder weight(@Nullable BigDecimal weight);
        public abstract Exercise build();
    }
}
