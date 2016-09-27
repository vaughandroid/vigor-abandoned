package vaughandroid.vigor.data.exercise.type;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.exercise.type.ExerciseTypeId;

/**
 * Maps between {@link ExerciseType} and {@link ExerciseTypeDto}
 *
 * @author Chris
 */
public class ExerciseTypeMapper {

  @Inject ExerciseTypeMapper() {
  }

  public ExerciseType fromDto(ExerciseTypeDto dto) {
    return ExerciseType.builder().id(ExerciseTypeId.create(dto.guid)).name(dto.name).build();
  }

  public ExerciseTypeDto fromExerciseType(ExerciseType exerciseType) {
    ExerciseTypeDto dto = new ExerciseTypeDto();
    dto.guid = exerciseType.guid();
    dto.name = exerciseType.name();
    return dto;
  }

  public Map<ExerciseTypeId, ExerciseType> fromDtoMap(Map<String, ExerciseTypeDto> dtoMap) {
    Map<ExerciseTypeId, ExerciseType> exerciseTypeMap = new HashMap<>();
    for (Map.Entry<String, ExerciseTypeDto> pair : dtoMap.entrySet()) {
      ExerciseType exerciseType = fromDto(pair.getValue());
      exerciseTypeMap.put(exerciseType.id(), exerciseType);
    }
    return exerciseTypeMap;
  }
}
