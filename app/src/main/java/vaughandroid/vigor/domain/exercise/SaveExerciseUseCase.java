package vaughandroid.vigor.domain.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import javax.inject.Inject;
import rx.Observable;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;
import vaughandroid.vigor.domain.usecase.ObservableUseCase;
import vaughandroid.vigor.domain.usecase.UseCase;

/**
 * {@link UseCase} for adding new {@link Exercise} data.
 *
 * @author Chris
 */
public class SaveExerciseUseCase extends ObservableUseCase<Exercise> {

  private final ExerciseRepository repository;
  private @Nullable Exercise exercise;

  @Inject
  public SaveExerciseUseCase(SchedulingPolicy schedulingPolicy, ExerciseRepository repository) {
    super(schedulingPolicy);
    this.repository = repository;
  }

  public SaveExerciseUseCase setExercise(@NonNull Exercise exercise) {
    this.exercise = exercise;
    return this;
  }

  @Override protected @NonNull Observable<Exercise> createObservable() {
    if (exercise == null) {
      throw new IllegalStateException("exercise not set");
    }
    return repository.addExercise(exercise);
    // XXX: Should update Workout too
  }
}
