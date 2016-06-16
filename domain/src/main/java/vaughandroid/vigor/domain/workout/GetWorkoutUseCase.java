package vaughandroid.vigor.domain.workout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import rx.Observable;
import vaughandroid.vigor.domain.usecase.UseCase;

/**
 * TODO: javadoc
 *
 *
 * @author Chris
 */
public class GetWorkoutUseCase implements UseCase<Workout> {

    private final WorkoutRepository repository;
    @Nullable
    private WorkoutId workoutId;

    @Inject
    public GetWorkoutUseCase(WorkoutRepository repository) {
        this.repository = repository;
    }

    public void setWorkoutId(@NotNull WorkoutId workoutId) {
        this.workoutId = workoutId;
    }

    @Override
    public Observable<Workout> createObservable() {
        if (workoutId == null) {
            throw new IllegalStateException("workoutId not set");
        }
        return repository.getWorkout(workoutId);
    }
}
