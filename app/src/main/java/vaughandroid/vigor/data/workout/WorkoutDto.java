package vaughandroid.vigor.data.workout;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;
import vaughandroid.vigor.data.exercise.ExerciseDto;
import vaughandroid.vigor.domain.workout.Workout;

/**
 * Data Transfer Object for a {@link Workout} instance
 *
 * @author Chris
 */
@SuppressWarnings("NullableProblems") public class WorkoutDto {

  @NonNull public String guid;

  @NonNull public List<ExerciseDto> exerciseDtos = new ArrayList<>();
}
