package vaughandroid.vigor.domain.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.common.base.Preconditions;
import javax.inject.Inject;
import rx.Completable;
import rx.Observable;
import rx.Single;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;
import vaughandroid.vigor.domain.usecase.CompletableUseCase;
import vaughandroid.vigor.domain.usecase.ObservableUseCase;
import vaughandroid.vigor.domain.usecase.SingleUseCase;
import vaughandroid.vigor.domain.usecase.UseCase;
import vaughandroid.vigor.domain.workout.GetWorkoutUseCase;
import vaughandroid.vigor.domain.workout.SaveWorkoutUseCase;
import vaughandroid.vigor.domain.workout.Workout;

/**
 * {@link UseCase} for adding new {@link Exercise} data.
 *
 * @author Chris
 */
public class SaveExerciseUseCase extends SingleUseCase<Exercise> {

  private final ExerciseRepository repository;
  private final GetWorkoutUseCase getWorkoutUseCase;
  private final SaveWorkoutUseCase saveWorkoutUseCase;

  private @Nullable Exercise exercise;

  @Inject
  public SaveExerciseUseCase(SchedulingPolicy schedulingPolicy, ExerciseRepository repository,
      GetWorkoutUseCase getWorkoutUseCase, SaveWorkoutUseCase saveWorkoutUseCase) {
    super(schedulingPolicy);
    this.repository = repository;
    this.getWorkoutUseCase = getWorkoutUseCase;
    this.saveWorkoutUseCase = saveWorkoutUseCase;
  }

  public SaveExerciseUseCase setExercise(@NonNull Exercise exercise) {
    this.exercise = exercise;
    return this;
  }

  @Override protected @NonNull Single<Exercise> createSingle() {
    if (exercise == null) {
      throw new IllegalStateException("exercise not set");
    }
    Single<Workout> saveWorkout = getWorkoutUseCase.setWorkoutId(exercise.workoutId())
        .perform()
        .toSingle()
        .flatMap(workout -> {
          workout.exercises().add(exercise);
          return saveWorkoutUseCase.setWorkout(workout).perform().toSingle();
        });

    return Single.zip(
        repository.addExercise(exercise).toSingle(),
        saveWorkout,
        (exercise, workout) -> exercise);
  }
}
