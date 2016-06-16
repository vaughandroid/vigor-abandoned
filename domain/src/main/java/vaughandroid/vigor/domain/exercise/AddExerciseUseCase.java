package vaughandroid.vigor.domain.exercise;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import rx.Observable;
import vaughandroid.vigor.domain.usecase.UseCase;

/**
 * {@link UseCase} for adding new {@link Exercise} data.
 *
 * @author Chris
 */
public class AddExerciseUseCase implements UseCase<Exercise> {

    private final ExerciseRepository repository;
    private @Nullable Exercise exercise;

    @Inject
    public AddExerciseUseCase(ExerciseRepository repository) {
        this.repository = repository;
    }

    public void setExercise(@NotNull Exercise exercise) {
        this.exercise = exercise;
    }

    @Override
    public @NotNull Observable<Exercise> createObservable() {
        if (exercise == null) {
            throw new IllegalStateException("exercise not set");
        }
        return repository.addExercise(exercise);
    }
}
