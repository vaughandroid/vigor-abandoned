package vaughandroid.vigor.data.exercise;

import android.support.annotation.NonNull;
import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.exercise.type.ExerciseTypeId;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * Map between {@link Exercise} and {@link ExerciseDto}
 *
 * @author Chris
 */
public class ExerciseMapper {

  @Inject ExerciseMapper() {
  }

  public Exercise fromDto(@NonNull ExerciseDto dto,
      @NonNull Map<ExerciseTypeId, ExerciseType> exerciseTypeMap) {
    Exercise.Builder builder = Exercise.builder()
        .id(ExerciseId.create(dto.guid))
        .workoutId(WorkoutId.create(dto.workout.guid))
        .reps(dto.reps)
        .weight(dto.weight);
    if (dto.type != null) {
      ExerciseTypeId exerciseTypeId = ExerciseTypeId.create(dto.type.guid);
      if (!Objects.equal(exerciseTypeId, ExerciseTypeId.UNASSIGNED)) {
        builder.type(exerciseTypeMap.get(exerciseTypeId));
      }
    }
    return builder.build();
  }

  @NonNull public ExerciseDto fromExercise(Exercise exercise) {
    ExerciseDto dto = new ExerciseDto();
    dto.guid = exercise.id().guid();
    {
      ExerciseDto.WorkoutDto workoutDto = new ExerciseDto.WorkoutDto();
      workoutDto.guid = exercise.workoutGuid();
      dto.workout = workoutDto;
    }
    String typeGuid = exercise.typeGuid();
    if (typeGuid != null) {
      ExerciseDto.ExerciseTypeDto exerciseTypeDto = new ExerciseDto.ExerciseTypeDto();
      exerciseTypeDto.guid = typeGuid;
      dto.type = exerciseTypeDto;
    }
    dto.weight = exercise.weightAsString();
    dto.reps = exercise.reps();
    return dto;
  }

  @NonNull public List<Exercise> fromDtoList(@NonNull List<ExerciseDto> dtoList,
      @NonNull Map<ExerciseTypeId, ExerciseType> exerciseTypeMap) {
    List<Exercise> exercises = new ArrayList<>(dtoList.size());
    for (ExerciseDto dto : dtoList) {
      exercises.add(fromDto(dto, exerciseTypeMap));
    }
    return exercises;
  }

  @NonNull public List<ExerciseDto> fromExerciseList(List<Exercise> exercises) {
    List<ExerciseDto> dtos = new ArrayList<>(exercises.size());
    for (Exercise exercise : exercises) {
      dtos.add(fromExercise(exercise));
    }
    return dtos;
  }
}
