package vaughandroid.vigor.domain.workout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import rx.Observable;
import vaughandroid.vigor.domain.usecase.UseCase;

/**
 * TODO: javadoc
 *
 * @author chris.vaughan@laterooms.com
 */
public class AddWorkoutUseCase implements UseCase<Workout> {

    private final WorkoutRepository repository;
    @Nullable
    private Workout workout;

    @Inject
    public AddWorkoutUseCase(WorkoutRepository repository) {
        this.repository = repository;
    }

    public void setWorkout(@NotNull Workout workout) {
        this.workout = workout;
    }

    @Override
    public Observable<Workout> createObservable() {
        if (workout == null) {
            throw new NullPointerException("workout is null");
        }
        return repository.addWorkout(workout);
    }
}
