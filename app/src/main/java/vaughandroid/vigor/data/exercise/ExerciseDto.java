package vaughandroid.vigor.data.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data Transfer Object for {@link vaughandroid.vigor.domain.exercise.Exercise}
 *
 * @author Chris
 */
@SuppressWarnings("NullableProblems")
public class ExerciseDto {

    @NonNull public String guid;

    @NonNull public WorkoutDto workout;

    @NonNull public ExerciseTypeDto type;

    @Nullable public Integer reps;

    @Nullable public String weight;

    static class WorkoutDto {
        @NonNull public String guid;
    }

    static class ExerciseTypeDto {
        @NonNull public String guid;
    }
}
