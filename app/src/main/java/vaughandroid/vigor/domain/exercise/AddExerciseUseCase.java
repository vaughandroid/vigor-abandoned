package vaughandroid.vigor.domain.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import rx.Observable;
import vaughandroid.vigor.domain.usecase.UseCase;

/**
 * {@link UseCase} for adding new {@link vaughandroid.vigor.domain.exercise.Exercise} data.
 *
 * @author Chris
 */
public class AddExerciseUseCase implements UseCase<vaughandroid.vigor.domain.exercise.Exercise> {

    private final ExerciseRepository repository;
    private @Nullable vaughandroid.vigor.domain.exercise.Exercise exercise;

    @Inject
    public AddExerciseUseCase(ExerciseRepository repository) {
        this.repository = repository;
    }

    public void setExercise(@NonNull vaughandroid.vigor.domain.exercise.Exercise exercise) {
        this.exercise = exercise;
    }

    @Override
    public @NonNull Observable<vaughandroid.vigor.domain.exercise.Exercise> createObservable() {
        if (exercise == null) {
            throw new IllegalStateException("exercise not set");
        }
        return repository.addExercise(exercise);
    }
}
