package vaughandroid.vigor.data.workout;

import android.support.annotation.NonNull;
import java.util.Map;
import javax.inject.Inject;
import vaughandroid.vigor.data.exercise.ExerciseMapper;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.exercise.type.ExerciseTypeId;
import vaughandroid.vigor.domain.workout.Workout;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * Maps between {@link Workout} and {@link WorkoutDto}
 *
 * @author chris.vaughan@laterooms.com
 */
public class WorkoutMapper {

  private final ExerciseMapper exerciseMapper;

  @Inject public WorkoutMapper(ExerciseMapper exerciseMapper) {
    this.exerciseMapper = exerciseMapper;
  }

  @NonNull Workout fromDto(@NonNull WorkoutDto dto, Map<ExerciseTypeId, ExerciseType> exerciseTypeMap) {
    return Workout.builder()
        .id(WorkoutId.create(dto.guid))
        .exercises(exerciseMapper.fromDtoList(dto.exerciseDtos, exerciseTypeMap))
        .build();
  }

  @NonNull WorkoutDto fromWorkout(@NonNull Workout workout) {
    WorkoutDto dto = new WorkoutDto();
    dto.guid = workout.id().guid();
    dto.exerciseDtos = exerciseMapper.fromExerciseList(workout.exercises());
    return dto;
  }
}
