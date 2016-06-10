package vaughandroid.vigor.data.exercise;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

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

    // TODO: persist stuff somewhere
    private final HashMap<SavedExerciseId, SavedExercise> lookup = new HashMap<>();

    public @NotNull Observable<SavedExercise> addExercise(@NotNull Exercise exercise) {
        SavedExercise savedExercise = SavedExercise.create(SavedExerciseId.create(1), exercise);
        lookup.put(savedExercise.id(), savedExercise);
        return Observable.just(savedExercise);
    }

    public @NotNull Observable<SavedExercise> getExercise(@NotNull SavedExerciseId id) {
        SavedExercise savedExercise = lookup.get(id);
        Observable<SavedExercise> result = Observable.empty();
        if (savedExercise != null) {
            Observable.just(savedExercise);
        }
        return result;
    }
}
