package vaughandroid.vigor.domain.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
    @Nullable private ExerciseId exerciseId;

    @Inject
    public GetExerciseUseCase(ExerciseRepository repository) {
        this.repository = repository;
    }

    public void setExerciseId(@NonNull ExerciseId exerciseId) {
        this.exerciseId = exerciseId;
    }

    @Override
    public Observable<Exercise> createObservable() {
        if (exerciseId == null) {
            throw new IllegalStateException("exerciseId not set");
        }
        return repository.getExercise(exerciseId);
    }
}