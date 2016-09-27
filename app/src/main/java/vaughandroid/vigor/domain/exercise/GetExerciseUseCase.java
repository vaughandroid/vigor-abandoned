package vaughandroid.vigor.domain.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import javax.inject.Inject;
import rx.Observable;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;
import vaughandroid.vigor.domain.usecase.ObservableUseCase;
import vaughandroid.vigor.domain.usecase.UseCase;

/**
 * {@link UseCase} for getting existing {@link Exercise} data.
 *
 * @author Chris
 */
public class GetExerciseUseCase extends ObservableUseCase<Exercise> {

  private final ExerciseRepository repository;
  @Nullable private ExerciseId exerciseId;

  @Inject
  public GetExerciseUseCase(SchedulingPolicy schedulingPolicy, ExerciseRepository repository) {
    super(schedulingPolicy);
    this.repository = repository;
  }

  public GetExerciseUseCase setExerciseId(@NonNull ExerciseId exerciseId) {
    this.exerciseId = exerciseId;
    return this;
  }

  @Override protected Observable<Exercise> create() {
    if (exerciseId == null) {
      throw new IllegalStateException("exerciseId not set");
    }
    return repository.getExercise(exerciseId);
  }
}
