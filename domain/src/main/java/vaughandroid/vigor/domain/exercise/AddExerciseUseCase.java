package vaughandroid.vigor.domain.exercise;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import rx.Observable;
import vaughandroid.vigor.domain.usecase.UseCase;

/**
 * {@link UseCase} for adding new exercise data.
 *
 * @author Chris
 */
public class AddExerciseUseCase implements UseCase<SavedExercise> {

    private final ExerciseRepository repository;
    private @Nullable Exercise data;

    @Inject
    public AddExerciseUseCase(ExerciseRepository repository) {
        this.repository = repository;
    }

    public void setData(@NotNull Exercise data) {
        this.data = data;
    }

    @Override
    public @NotNull Observable<SavedExercise> createObservable() {
        if (data == null) {
            throw new NullPointerException("data is null");
        }
        return repository.addExercise(data);
    }
}
