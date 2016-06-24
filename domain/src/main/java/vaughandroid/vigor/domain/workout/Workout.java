package vaughandroid.vigor.domain.workout;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

import vaughandroid.vigor.domain.exercise.Exercise;

/**
 * TODO: javadoc
 *
 * @author Chris
 */
@AutoValue
public abstract class Workout implements Serializable {

    public static Builder builder() {
        return new AutoValue_Workout.Builder()
                .id(WorkoutId.UNASSIGNED)
                .exercises(ImmutableList.<Exercise>of());
    }

    @NotNull
    public abstract WorkoutId id();
    public abstract Workout withId(WorkoutId workoutId);

    @NotNull
    public abstract ImmutableList<Exercise> exercises();
    public abstract Workout withExercises(@NotNull ImmutableList<Exercise> exercises);

    @AutoValue.Builder
    public static abstract class Builder {

        public abstract Builder id(@NotNull WorkoutId id);
        public abstract Builder exercises(@NotNull ImmutableList<Exercise> exercises);

        public Builder exercises(List<Exercise> exercises) {
            exercises(ImmutableList.copyOf(exercises));
            return this;
        }

        public abstract Workout build();
    }
}
