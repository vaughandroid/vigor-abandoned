package vaughandroid.vigor.domain.workout;

import org.jetbrains.annotations.NotNull;

import rx.Observable;

/**
 * Interface for a class which can store & retrieve workout data.
 *
 * @author Chris
 */
public interface WorkoutRepository {

    @NotNull Observable<Workout> addWorkout(@NotNull Workout workout);
    @NotNull Observable<Workout> getWorkout(@NotNull vaughandroid.vigor.domain.workout.WorkoutId id);
}
