package vaughandroid.vigor.domain.exercise.type;

import android.support.annotation.NonNull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import rx.Observable;

/**
 * Interface for a class which can store & retrieve {@link ExerciseType} data.
 *
 * @author Chris
 */
public interface ExerciseTypeRepository {

    @NonNull Observable<ExerciseType> addExerciseType(@NonNull ExerciseType exerciseType);
    @NonNull Observable<ExerciseType> getExerciseType(@NonNull ExerciseTypeId id);
    @NonNull Observable<ImmutableList<ExerciseType>> getExerciseTypeList();
    @NonNull Observable<ImmutableMap<ExerciseTypeId, ExerciseType>> getExerciseTypeMap();
}
