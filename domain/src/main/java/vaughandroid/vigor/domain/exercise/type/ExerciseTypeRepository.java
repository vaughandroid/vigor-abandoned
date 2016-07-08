package vaughandroid.vigor.domain.exercise.type;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import rx.Observable;

/**
 * Interface for a class which can store & retrieve {@link ExerciseType} data.
 *
 * @author Chris
 */
public interface ExerciseTypeRepository {

    @NotNull Observable<ExerciseType> addExerciseType(@NotNull ExerciseType exerciseType);
    @NotNull Observable<ExerciseType> getExerciseType(@NotNull ExerciseTypeId id);
    @NotNull Observable<List<ExerciseType>> getExerciseTypeList();
}
