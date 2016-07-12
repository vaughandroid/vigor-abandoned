package vaughandroid.vigor.domain.exercise.type;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;

/**
 * Interface for a class which can store & retrieve {@link vaughandroid.vigor.domain.exercise.type.ExerciseType} data.
 *
 * @author Chris
 */
public interface ExerciseTypeRepository {

    @NonNull Observable<vaughandroid.vigor.domain.exercise.type.ExerciseType> addExerciseType(@NonNull vaughandroid.vigor.domain.exercise.type.ExerciseType exerciseType);
    @NonNull Observable<vaughandroid.vigor.domain.exercise.type.ExerciseType> getExerciseType(@NonNull ExerciseTypeId id);
    @NonNull Observable<List<vaughandroid.vigor.domain.exercise.type.ExerciseType>> getExerciseTypeList();
}
