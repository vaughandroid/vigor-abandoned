package vaughandroid.vigor.data.exercise;

import android.support.annotation.NonNull;

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

    @Inject
    ExerciseMapper() {}

    public Exercise fromDto(@NonNull ExerciseDto dto, @NonNull Map<ExerciseTypeId, ExerciseType> exerciseTypeMap) {
        return Exercise.builder()
                .id(ExerciseId.create(dto.guid))
                .type(exerciseTypeMap.get(ExerciseTypeId.create(dto.type.guid))) // TODO: 27/07/2016 Handle unavailable type
                .workoutId(WorkoutId.create(dto.workout.guid))
                .build();
    }

    public ExerciseDto fromExercise(Exercise exercise) {
        ExerciseDto dto = new ExerciseDto();
        dto.guid = exercise.id().guid();
        {
            ExerciseDto.WorkoutDto workoutDto = new ExerciseDto.WorkoutDto();
            workoutDto.guid = exercise.workoutGuid();
            dto.workout = workoutDto;
        }
        {
            ExerciseDto.ExerciseTypeDto exerciseTypeDto = new ExerciseDto.ExerciseTypeDto();
            exerciseTypeDto.guid = exercise.typeGuid();
            dto.type = exerciseTypeDto;
        }
        dto.weight = exercise.weightAsString();
        dto.reps = exercise.reps();
        return null;
    }

    public List<Exercise> fromDtoList(@NonNull List<ExerciseDto> dtoList,
            @NonNull Map<ExerciseTypeId, ExerciseType> exerciseTypeMap) {
        List<Exercise> exercises = new ArrayList<>(dtoList.size());
        for (ExerciseDto dto : dtoList) {
            exercises.add(fromDto(dto, exerciseTypeMap));
        }
        return exercises;
    }
}
