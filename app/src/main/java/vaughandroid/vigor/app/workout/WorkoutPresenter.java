package vaughandroid.vigor.app.workout;

import android.support.annotation.NonNull;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import vaughandroid.vigor.app.exercise.ExerciseContract;
import vaughandroid.vigor.app.mvp.BasePresenter;
import vaughandroid.vigor.app.workout.WorkoutContract.View;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.workout.AddWorkoutUseCase;
import vaughandroid.vigor.domain.workout.GetWorkoutUseCase;
import vaughandroid.vigor.domain.workout.Workout;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * MVP presenter for {@link WorkoutContract}
 *
 * @author Chris
 */
public class WorkoutPresenter extends BasePresenter<View> implements WorkoutContract.Presenter {

  private final AddWorkoutUseCase addWorkoutUseCase;
  private final GetWorkoutUseCase getWorkoutUseCase;

  private Workout workout;

  @Inject public WorkoutPresenter(ActivityLifecycleProvider activityLifecycleProvider,
      AddWorkoutUseCase addWorkoutUseCase, GetWorkoutUseCase getWorkoutUseCase) {
    super(activityLifecycleProvider);
    this.getWorkoutUseCase = getWorkoutUseCase;
    this.addWorkoutUseCase = addWorkoutUseCase;
  }

  @Override protected void initView(@NonNull View view) {
    if (workout != null) {
      view.setExercises(workout.exercises());
    }
  }

  @Override public void setWorkoutId(@NonNull WorkoutId workoutId) {
    if (Objects.equal(workoutId, WorkoutId.UNASSIGNED)) {
      workout = Workout.builder().build();
    } else {
      getWorkoutUseCase.perform()
          .compose(activityLifecycleProvider.bindToLifecycle())
          .subscribe(WorkoutPresenter.this::setWorkout, WorkoutPresenter.this::showError);
    }
  }

  @Override public void onAddExercise() {
    // TODO: 19/06/2016 Find a better way of dealing with IDs
    View view = getView();
    if (view != null) {
      view.goToAddNewExercise(workout.id());
    }
  }

  private void setWorkout(Workout workout) {
    this.workout = workout;
    View view = getView();
    if (view != null) {
      view.setExercises(workout.exercises());
    }
  }

  private void showError(Throwable t) {
    View view = getView();
    if (view != null) {
      view.showError();
    }
  }

  @Override public void onOpenExercise(@NonNull Exercise exercise) {
    View view = getView();
    if (view != null) {
      view.goToEditExistingExercise(workout.id(), exercise.id());
    }
  }

  @Override public void onExerciseAdded(@NonNull Exercise exercise) {
    List<Exercise> exercises = new ArrayList<>(workout.exercises());
    exercises.add(exercise);
    setWorkout(workout.withExercises(ImmutableList.copyOf(exercises)));
  }

  @Override public void onExerciseUpdated(@NonNull Exercise exercise) {
    List<Exercise> exercises = new ArrayList<>(workout.exercises());
    for (int i = 0; i < exercises.size(); i++) {
      if (exercises.get(i).id().equals(exercise.id())) {
        exercises.set(i, exercise);
        break;
      }
    }
    setWorkout(workout.withExercises(ImmutableList.copyOf(exercises)));
  }

  @Override public void onError(Throwable t) {
    logger.error("Error", t);
    View view = getView();
    if (view != null) {
      view.showError();
    }
  }
}
