package vaughandroid.vigor.data.exercise;

import android.support.annotation.NonNull;

import com.google.common.base.Objects;

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

    @NonNull
    @Override
    public Observable<Exercise> addExercise(@NonNull Exercise exercise) {
        if (Objects.equal(exercise.id(), ExerciseId.UNASSIGNED)) {
            exercise = exercise.withId(ExerciseId.create(guidFactory.newGuid()));
        }
        lookup.put(exercise.id(), exercise);
        return Observable.just(exercise);
    }

    @NonNull
    @Override
    public Observable<Exercise> getExercise(@NonNull ExerciseId id) {
        Observable<Exercise> result = Observable.empty();
        if (lookup.containsKey(id)) {
            Observable.just(lookup.get(id));
        }
        return result;
    }
}
