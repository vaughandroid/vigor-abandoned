package vaughandroid.vigor.domain.workout;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import rx.Observable;
import vaughandroid.vigor.domain.usecase.UseCase;

/**
 * Use case for retrieving a {@link Workout}
 *
 *
 * @author Chris
 */
public class GetWorkoutUseCase implements UseCase<vaughandroid.vigor.domain.workout.Workout> {

    private final WorkoutRepository repository;
    @Nullable
    private vaughandroid.vigor.domain.workout.WorkoutId workoutId;

    @Inject
    public GetWorkoutUseCase(WorkoutRepository repository) {
        this.repository = repository;
    }

    public void setWorkoutId(@NonNull vaughandroid.vigor.domain.workout.WorkoutId workoutId) {
        this.workoutId = workoutId;
    }

    @Override
    public Observable<vaughandroid.vigor.domain.workout.Workout> createObservable() {
        if (workoutId == null) {
            throw new IllegalStateException("workoutId not set");
        }
        return repository.getWorkout(workoutId);
    }
}
