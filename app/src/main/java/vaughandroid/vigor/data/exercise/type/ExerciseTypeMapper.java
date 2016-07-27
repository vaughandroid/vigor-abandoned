package vaughandroid.vigor.data.exercise.type;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.exercise.type.ExerciseTypeId;

/**
 * Maps between {@link ExerciseType} and {@link ExerciseTypeDto}
 *
 * @author Chris
 */
public class ExerciseTypeMapper {

    @Inject ExerciseTypeMapper() {}

    ExerciseType fromDto(ExerciseTypeDto dto) {
        return ExerciseType.builder()
                .id(ExerciseTypeId.create(dto.guid))
                .name(dto.name)
                .build();
    }

    ExerciseTypeDto fromExerciseType(ExerciseType exerciseType) {
        ExerciseTypeDto dto = new ExerciseTypeDto();
        dto.guid = exerciseType.guid();
        dto.name = exerciseType.name();
        return dto;
    }

    public List<ExerciseType> fromDtoList(List<ExerciseTypeDto> dtos) {
        List<ExerciseType> exerciseTypes = new ArrayList<>(dtos.size());
        for (ExerciseTypeDto dto : dtos) {
            exerciseTypes.add(fromDto(dto));
        }
        return exerciseTypes;
    }
}
