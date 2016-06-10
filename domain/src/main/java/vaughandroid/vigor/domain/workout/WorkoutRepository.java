package vaughandroid.vigor.domain.workout;

import org.jetbrains.annotations.NotNull;

import rx.Observable;

/**
 * Interface for a class which can store & retrieve workout data.
 *
 * @author chris.vaughan@laterooms.com
 */
public interface WorkoutRepository {

    @NotNull Observable<Workout> addWorkout(@NotNull Workout workout);
    @NotNull Observable<Workout> getWorkout(@NotNull WorkoutId id);
}
