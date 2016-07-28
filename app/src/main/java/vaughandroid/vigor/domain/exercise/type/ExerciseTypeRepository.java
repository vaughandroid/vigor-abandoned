package vaughandroid.vigor.domain.exercise.type;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Interface for a class which can store & retrieve {@link ExerciseType} data.
 *
 * @author Chris
 */
public interface ExerciseTypeRepository {

    @NonNull Observable<ExerciseType> addExerciseType(@NonNull ExerciseType exerciseType);
    @NonNull Observable<ExerciseType> getExerciseType(@NonNull ExerciseTypeId id);
    @NonNull Observable<List<ExerciseType>> getExerciseTypeList();
    @NonNull Observable<Map<ExerciseTypeId, ExerciseType>> getExerciseTypeMap();
}
