package vaughandroid.vigor.data.exercise;

import javax.inject.Inject;

import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * Map between {@link Exercise} and {@link ExerciseDto}
 *
 * @author Chris
 */
public class ExerciseMapper {

    @Inject
    ExerciseMapper() {}

    Exercise fromDto(ExerciseDto dto, ExerciseType type) {
        return Exercise.builder()
                .id(ExerciseId.create(dto.guid))
                .type(type)
                .workoutId(WorkoutId.create(dto.workout.guid))
                .build();
    }

    ExerciseDto fromExercise(Exercise exercise) {
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
}
