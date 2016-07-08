package vaughandroid.vigor.domain.exercise.type;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import rx.Observable;

/**
 * Interface for a class which can store & retrieve {@link vaughandroid.vigor.domain.exercise.type.ExerciseType} data.
 *
 * @author Chris
 */
public interface ExerciseTypeRepository {

    @NotNull Observable<vaughandroid.vigor.domain.exercise.type.ExerciseType> addExerciseType(@NotNull vaughandroid.vigor.domain.exercise.type.ExerciseType exerciseType);
    @NotNull Observable<vaughandroid.vigor.domain.exercise.type.ExerciseType> getExerciseType(@NotNull ExerciseTypeId id);
    @NotNull Observable<List<vaughandroid.vigor.domain.exercise.type.ExerciseType>> getExerciseTypeList();
}
