package vaughandroid.vigor.domain.exercise;

import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.math.BigDecimal;

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
                .id(ExerciseId.UNASSIGNED);
    }

    @NotNull
    public abstract ExerciseId id();
    public abstract Exercise withId(@NotNull ExerciseId id);

    @NotNull
    public abstract WorkoutId workoutId();

    @Nullable
    public abstract Integer reps();
    public abstract Exercise withReps(Integer reps);

    @Nullable
    public abstract BigDecimal weight();
    public abstract Exercise withWeight(@NotNull BigDecimal weight);

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(@NotNull ExerciseId id);
        public abstract Builder workoutId(@NotNull WorkoutId workoutId);
        public abstract Builder reps(@Nullable Integer reps);
        public abstract Builder weight(@NotNull BigDecimal weight);
        public abstract Exercise build();
    }
}
