package vaughandroid.vigor.domain.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import javax.inject.Inject;
import rx.Single;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;
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

  @Override protected @NonNull Single<Exercise> create() {
    if (exercise == null) {
      throw new IllegalStateException("exercise not set");
    }

    Single<Workout> getWorkout =
        getWorkoutUseCase.setWorkoutId(exercise.workoutId()).getObservable().take(1).toSingle();

    /* 1. Save the Exercise & retrieve the Workout.
     * 2. Add the saved Exercise to the Workout & save that.
     * 3. Return the saved Exercise.
     */
    return Single.zip(
        getWorkout,
        repository.addExercise(exercise),
        Pair::new)
        .flatMap(pair -> {
          Workout workout = pair.first;
          Exercise savedExercise = pair.second;
          workout.addExercise(savedExercise);
          return saveWorkoutUseCase.setWorkout(workout)
              .getSingle()
              .map(savedWorkout -> savedExercise);
        });
  }
}
