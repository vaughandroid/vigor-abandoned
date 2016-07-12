package vaughandroid.vigor.domain.workout;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import rx.Observable;
import vaughandroid.vigor.domain.usecase.UseCase;

/**
 * TODO: javadoc
 *
 * @author Chris
 */
public class AddWorkoutUseCase implements UseCase<vaughandroid.vigor.domain.workout.Workout> {

    private final WorkoutRepository repository;
    @Nullable
    private vaughandroid.vigor.domain.workout.Workout workout;

    @Inject
    public AddWorkoutUseCase(WorkoutRepository repository) {
        this.repository = repository;
    }

    public void setWorkout(@NonNull vaughandroid.vigor.domain.workout.Workout workout) {
        this.workout = workout;
    }

    @Override
    public Observable<vaughandroid.vigor.domain.workout.Workout> createObservable() {
        if (workout == null) {
            throw new IllegalStateException("workout not set");
        }
        return repository.addWorkout(workout);
    }
}
