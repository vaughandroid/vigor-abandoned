package vaughandroid.vigor.data.workout;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import javax.inject.Inject;

import rx.Observable;
import vaughandroid.vigor.data.utils.GuidFactory;
import vaughandroid.vigor.domain.workout.Workout;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * TODO: javadoc
 *
 * @author Chris
 */
public class WorkoutRepository implements vaughandroid.vigor.domain.workout.WorkoutRepository {

    // TODO: Persist somewhere
    private final HashMap<WorkoutId, Workout> lookup = new HashMap<>();
    private final GuidFactory guidFactory;

    @Inject
    public WorkoutRepository(GuidFactory guidFactory) {
        this.guidFactory = guidFactory;
    }

    @NotNull
    @Override
    public Observable<Workout> addWorkout(@NotNull Workout workout) {
        if (workout.id() == null) {
            workout = workout.withId(WorkoutId.create(guidFactory.newGuid()));
        }
        lookup.put(workout.id(), workout);
        return Observable.just(workout);
    }

    @NotNull
    @Override
    public Observable<Workout> getWorkout(@NotNull WorkoutId id) {
        Observable<Workout> result = Observable.empty();
        if (lookup.containsKey(id)) {
            result = Observable.just(lookup.get(id));
        }
        return result;
    }
}
