package vaughandroid.vigor.domain.exercise;

import com.google.auto.value.AutoValue;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
        return new $AutoValue_Exercise.Builder()
                .id(ExerciseId.UNASSIGNED)
                .type(ExerciseType.UNSET);
    }

    @NonNull
    public abstract ExerciseId id();
    public abstract Exercise withId(@NonNull ExerciseId id);

    @NonNull
    public abstract WorkoutId workoutId();

    @NonNull
    public abstract ExerciseType type();
    public abstract Exercise withType(@NonNull ExerciseType type);

    @Nullable
    public abstract Integer reps();
    public abstract Exercise withReps(@Nullable Integer reps);

    @Nullable
    public abstract BigDecimal weight();
    public abstract Exercise withWeight(@Nullable BigDecimal weight);

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(@NonNull ExerciseId id);
        public abstract Builder type(@NonNull ExerciseType type);
        public abstract Builder workoutId(@NonNull WorkoutId workoutId);
        public abstract Builder reps(@Nullable Integer reps);
        public abstract Builder weight(@Nullable BigDecimal weight);
        public abstract Exercise build();
    }
}
