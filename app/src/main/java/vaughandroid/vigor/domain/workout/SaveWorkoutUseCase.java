package vaughandroid.vigor.domain.workout;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import javax.inject.Inject;
import rx.Single;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;
import vaughandroid.vigor.domain.usecase.SingleUseCase;

/**
 * Use case for adding a new {@link Workout}
 *
 * @author Chris
 */
public class SaveWorkoutUseCase extends SingleUseCase<Workout> {

  private final WorkoutRepository repository;
  @Nullable private Workout workout;

  @Inject
  public SaveWorkoutUseCase(SchedulingPolicy schedulingPolicy, WorkoutRepository repository) {
    super(schedulingPolicy);
    this.repository = repository;
  }

  public SaveWorkoutUseCase setWorkout(@NonNull Workout workout) {
    this.workout = workout;
    return this;
  }

  @Override protected Single<Workout> create() {
    if (workout == null) {
      throw new IllegalStateException("workout not set");
    }
    return repository.addWorkout(workout);
  }
}
