package vaughandroid.vigor.domain.workout;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import javax.inject.Inject;
import rx.Observable;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;
import vaughandroid.vigor.domain.usecase.ObservableUseCase;

/**
 * Use case for retrieving a {@link Workout}
 *
 * @author Chris
 */
public class GetWorkoutUseCase extends ObservableUseCase<Workout> {

  private final WorkoutRepository repository;
  @Nullable private WorkoutId workoutId;

  @Inject
  public GetWorkoutUseCase(SchedulingPolicy schedulingPolicy, WorkoutRepository repository) {
    super(schedulingPolicy);
    this.repository = repository;
  }

  public void setWorkoutId(@NonNull WorkoutId workoutId) {
    this.workoutId = workoutId;
  }

  @Override protected Observable<Workout> createObservable() {
    if (workoutId == null) {
      throw new IllegalStateException("workoutId not set");
    }
    return repository.getWorkout(workoutId);
  }
}
