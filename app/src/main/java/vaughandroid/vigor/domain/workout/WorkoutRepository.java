package vaughandroid.vigor.domain.workout;

import android.support.annotation.NonNull;

import rx.Observable;

/**
 * Interface for a class which can store & retrieve workout data.
 *
 * @author Chris
 */
public interface WorkoutRepository {

    @NonNull Observable<Workout> addWorkout(@NonNull Workout workout);
    @NonNull Observable<Workout> getWorkout(@NonNull vaughandroid.vigor.domain.workout.WorkoutId id);
}
