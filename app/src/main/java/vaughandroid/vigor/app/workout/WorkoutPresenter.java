package vaughandroid.vigor.app.workout;

import android.support.annotation.NonNull;
import com.google.common.base.Objects;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import java.util.concurrent.CancellationException;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Subscription;
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
public class WorkoutPresenter implements WorkoutContract.Presenter {

  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final ActivityLifecycleProvider activityLifecycleProvider;
  private final GetWorkoutUseCase getWorkoutUseCase;
  private final SaveWorkoutUseCase saveWorkoutUseCase;

  private View view;
  private Workout workout;

  @Inject public WorkoutPresenter(ActivityLifecycleProvider activityLifecycleProvider,
      GetWorkoutUseCase getWorkoutUseCase, SaveWorkoutUseCase saveWorkoutUseCase) {
    this.activityLifecycleProvider = activityLifecycleProvider;
    this.getWorkoutUseCase = getWorkoutUseCase;
    this.saveWorkoutUseCase = saveWorkoutUseCase;
  }

  @Override public void init(@NonNull View view, @NonNull WorkoutId workoutId) {
    this.view = view;
    view.showLoading();

    if (Objects.equal(workoutId, WorkoutId.UNASSIGNED)) {
      // Save a new Workout & get its ID
      saveWorkoutUseCase.setWorkout(Workout.builder().build())
          .getSingle()
          .compose(activityLifecycleProvider.<Workout>bindUntilEvent(ActivityEvent.DESTROY).forSingle())
          .subscribe(workout -> loadWorkout(workout.id()), WorkoutPresenter.this::onError);
    } else {
      loadWorkout(workoutId);
    }
  }

  private Subscription loadWorkout(@NonNull WorkoutId workoutId) {
    return getWorkoutUseCase.setWorkoutId(workoutId)
        .getObservable()
        .compose(activityLifecycleProvider.bindUntilEvent(ActivityEvent.DESTROY))
        .subscribe(WorkoutPresenter.this::setWorkout, WorkoutPresenter.this::onError);
  }

  @Override public void onAddExercise() {
    view.goToAddNewExercise(workout.id());
  }

  private void setWorkout(Workout workout) {
    this.workout = workout;
    View view = this.view;
    view.setExercises(workout.exercises());
    view.showContent();
  }

  @Override public void onOpenExercise(@NonNull Exercise exercise) {
    view.goToEditExistingExercise(workout.id(), exercise.id());
  }

  @Override public void onError(Throwable t) {
    if (!(t instanceof CancellationException)) {
      logger.error("Error", t);
      view.showError();
    }
  }
}
