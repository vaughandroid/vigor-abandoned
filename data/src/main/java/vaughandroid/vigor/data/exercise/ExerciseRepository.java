package vaughandroid.vigor.data.exercise;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import rx.Observable;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.ExerciseId;

/**
 * Implementation of {@link vaughandroid.vigor.domain.exercise.ExerciseRepository}
 *
 * @author Chris
 */
public class ExerciseRepository implements vaughandroid.vigor.domain.exercise.ExerciseRepository {

    // TODO: persist somewhere
    private final HashMap<ExerciseId, Exercise> lookup = new HashMap<>();

    @NotNull
    @Override
    public Observable<Exercise> addExercise(@NotNull Exercise exercise) {
        lookup.put(exercise.id(), exercise);
        return Observable.just(exercise);
    }

    @NotNull
    @Override
    public Observable<Exercise> getExercise(@NotNull ExerciseId id) {
        Observable<Exercise> result = Observable.empty();
        if (lookup.containsKey(id)) {
            Observable.just(lookup.get(id));
        }
        return result;
    }
}
