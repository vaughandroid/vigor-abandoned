package vaughandroid.vigor.app.workout;

import android.support.annotation.NonNull;
import com.google.common.base.Objects;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import javax.inject.Inject;
import vaughandroid.vigor.app.mvp.BasePresenter;
import vaughandroid.vigor.app.workout.WorkoutContract.View;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.workout.GetWorkoutUseCase;
import vaughandroid.vigor.domain.workout.SaveWorkoutUseCase;
import vaughandroid.vigor.domain.workout.Workout;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * MVP presenter for {@link WorkoutContract}
 *
 * @author Chris
 */
public class WorkoutPresenter extends BasePresenter<View> implements WorkoutContract.Presenter {

  private final GetWorkoutUseCase getWorkoutUseCase;
  private final SaveWorkoutUseCase saveWorkoutUseCase;

  private Workout workout;

  @Inject public WorkoutPresenter(ActivityLifecycleProvider activityLifecycleProvider,
      GetWorkoutUseCase getWorkoutUseCase, SaveWorkoutUseCase saveWorkoutUseCase) {
    super(activityLifecycleProvider);
    this.getWorkoutUseCase = getWorkoutUseCase;
    this.saveWorkoutUseCase = saveWorkoutUseCase;
  }

  @Override public void init(@NonNull View view, @NonNull WorkoutId workoutId) {
    setView(view);
    view.showLoading();

    if (Objects.equal(workoutId, WorkoutId.UNASSIGNED)) {
      setWorkout(Workout.builder().build());
    } else {
      getWorkoutUseCase.setWorkoutId(workoutId);
      getWorkoutUseCase.perform()
          .compose(activityLifecycleProvider.bindToLifecycle())
          .subscribe(WorkoutPresenter.this::setWorkout, WorkoutPresenter.this::onError);
    }
  }

  @Override public void onAddExercise() {
    getView().goToAddNewExercise(workout.id());
  }

  private void setWorkout(Workout workout) {
    this.workout = workout;
    View view = getView();
    view.setExercises(workout.exercises());
    view.showContent();
  }

  @Override public void onOpenExercise(@NonNull Exercise exercise) {
    getView().goToEditExistingExercise(workout.id(), exercise.id());
  }

  @Override public void onError(Throwable t) {
    logger.error("Error", t);
    getView().showError();
  }
}
