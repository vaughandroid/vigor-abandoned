package vaughandroid.vigor.domain.workout;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import javax.inject.Inject;
import rx.Observable;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;
import vaughandroid.vigor.domain.usecase.ObservableUseCase;

/**
 * Use case for adding a new {@link Workout}
 *
 * @author Chris
 */
public class AddWorkoutUseCase extends ObservableUseCase<Workout> {

  private final WorkoutRepository repository;
  @Nullable private Workout workout;

  @Inject
  public AddWorkoutUseCase(SchedulingPolicy schedulingPolicy, WorkoutRepository repository) {
    super(schedulingPolicy);
    this.repository = repository;
  }

  public void setWorkout(@NonNull Workout workout) {
    this.workout = workout;
  }

  @Override public Observable<Workout> createObservable() {
    if (workout == null) {
      throw new IllegalStateException("workout not set");
    }
    return repository.addWorkout(workout);
  }
}
