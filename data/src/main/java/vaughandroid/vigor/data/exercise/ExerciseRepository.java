package vaughandroid.vigor.data.exercise;

import org.jetbrains.annotations.NotNull;

import rx.Observable;
import vaughandroid.vigor.domain.exercise.SavedExercise;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.SavedExerciseId;

/**
 * Implementation of {@link vaughandroid.vigor.domain.exercise.ExerciseRepository}
 *
 * @author Chris
 */
public class ExerciseRepository implements vaughandroid.vigor.domain.exercise.ExerciseRepository {

    public @NotNull Observable<SavedExercise> addExercise(@NotNull Exercise exercise) {
        // TODO: stub
        return Observable.just(SavedExercise.create(SavedExerciseId.create(1), exercise));
    }
}
