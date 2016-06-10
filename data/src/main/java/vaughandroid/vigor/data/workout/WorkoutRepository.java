package vaughandroid.vigor.data.workout;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import rx.Observable;
import vaughandroid.vigor.domain.workout.Workout;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * TODO: javadoc
 *
 * @author chris.vaughan@laterooms.com
 */
public class WorkoutRepository implements vaughandroid.vigor.domain.workout.WorkoutRepository {

    // TODO: Persist somewhere
    private final HashMap<WorkoutId, Workout> lookup = new HashMap<>();

    @NotNull
    @Override
    public Observable<Workout> addWorkout(@NotNull Workout workout) {
        if (workout.id() == null) {
            // TODO: Better to do this here, or when the instance is created?
            workout = workout.withId(WorkoutId.create(lookup.size() + 1));
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
