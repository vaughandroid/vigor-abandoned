package vaughandroid.vigor.data.workout;

import android.support.annotation.NonNull;

import java.util.List;

import vaughandroid.vigor.data.exercise.ExerciseDto;
import vaughandroid.vigor.domain.workout.Workout;

/**
 * Data Transfer Object for a {@link Workout} instance
 *
 * @author chris.vaughan@laterooms.com
 */
public class WorkoutDto {

    @NonNull
    String guid;

    @NonNull
    List<ExerciseDto> exerciseDtos;
}