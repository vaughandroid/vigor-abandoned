package vaughandroid.vigor.data.exercise;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import javax.inject.Inject;

import rx.Observable;
import vaughandroid.vigor.data.utils.GuidFactory;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.ExerciseId;

/**
 * Implementation of {@link vaughandroid.vigor.domain.exercise.ExerciseRepository}
 *
 * @author Chris
 */
public class ExerciseRepository implements vaughandroid.vigor.domain.exercise.ExerciseRepository {

    private final GuidFactory guidFactory;
    // TODO: persist somewhere
    private final HashMap<ExerciseId, Exercise> lookup = new HashMap<>();

    @Inject
    public ExerciseRepository(GuidFactory guidFactory) {
        this.guidFactory = guidFactory;
    }

    @NotNull
    @Override
    public Observable<Exercise> addExercise(@NotNull Exercise exercise) {
        if (exercise.id() == null) {
            // TODO: Better to do this here, or when the instance is created?
            exercise = exercise.withId(guidFactory.newGuid());
        }
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
