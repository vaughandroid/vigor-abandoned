package vaughandroid.vigor.domain.exercise;

import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import rx.Observable;
import vaughandroid.vigor.domain.usecase.UseCase;

/**
 * {@link UseCase} for getting existing {@link Exercise} data.
 *
 * @author Chris
 */
public class GetExerciseUseCase implements UseCase<Exercise> {

    private final ExerciseRepository repository;
    @Nullable private ExerciseId id;

    @Inject
    public GetExerciseUseCase(ExerciseRepository repository) {
        this.repository = repository;
    }

    public void setId(ExerciseId id) {
        this.id = id;
    }

    @Override
    public Observable<Exercise> createObservable() {
        if (id == null) {
            throw new NullPointerException("id not set");
        }
        return repository.getExercise(id);
    }
}
