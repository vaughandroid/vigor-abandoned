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

    // TODO: persist stuff somewhere
    private final HashMap<ExerciseId, Exercise> lookup = new HashMap<>();

    public @NotNull Observable<Exercise> addExercise(@NotNull Exercise exercise) {
        // TODO: Better to do this here, or when the exercise instance is created?
        if (exercise.id() == null) {
            exercise = exercise.withId(ExerciseId.create(lookup.size() + 1));
        }
        lookup.put(exercise.id(), exercise);
        return Observable.just(exercise);
    }

    public @NotNull Observable<Exercise> getExercise(@NotNull ExerciseId id) {
        Exercise savedExercise = lookup.get(id);
        Observable<Exercise> result = Observable.empty();
        if (savedExercise != null) {
            Observable.just(savedExercise);
        }
        return result;
    }
}
