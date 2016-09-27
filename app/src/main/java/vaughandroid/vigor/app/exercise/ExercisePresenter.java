package vaughandroid.vigor.app.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.common.base.Objects;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import java.math.BigDecimal;
import java.util.concurrent.CancellationException;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.functions.Action0;
import vaughandroid.vigor.app.di.ActivityScope;
import vaughandroid.vigor.app.exercise.ExerciseContract.View;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.exercise.GetExerciseUseCase;
import vaughandroid.vigor.domain.exercise.SaveExerciseUseCase;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * MVP presenter implementation for {@link ExerciseContract}
 *
 * @author Chris
 */
@ActivityScope public class ExercisePresenter implements ExerciseContract.Presenter {

  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final ActivityLifecycleProvider activityLifecycleProvider;
  private final SaveExerciseUseCase saveExerciseUseCase;
  private final GetExerciseUseCase getExerciseUseCase;

  private View view;
  private Exercise exercise;
  private boolean exerciseChanged;

  @Inject public ExercisePresenter(ActivityLifecycleProvider activityLifecycleProvider,
      SaveExerciseUseCase saveExerciseUseCase, GetExerciseUseCase getExerciseUseCase) {
    this.activityLifecycleProvider = activityLifecycleProvider;
    this.saveExerciseUseCase = saveExerciseUseCase;
    this.getExerciseUseCase = getExerciseUseCase;
  }

  @Override public void init(@NonNull View view, @NonNull WorkoutId workoutId,
      @NonNull ExerciseId exerciseId) {
    this.view = view;
    view.showLoading();

    if (Objects.equal(exerciseId, ExerciseId.UNASSIGNED)) {
      // Save a new Exercise & get its ID.
      Exercise exercise = Exercise.builder()
          .workoutId(workoutId)
          .build();
      saveExerciseUseCase.setExercise(exercise)
          .perform()
          .compose(activityLifecycleProvider.<Exercise>bindUntilEvent(ActivityEvent.DESTROY).forSingle())
          .subscribe(savedExercise -> loadExercise(savedExercise.id()), this::onError);
    } else {
      loadExercise(exerciseId);
    }
  }

  private void loadExercise(@NonNull ExerciseId exerciseId) {
    Observable<Exercise> exerciseObservable = getExerciseUseCase.setExerciseId(exerciseId)
        .perform()
        .compose(activityLifecycleProvider.bindUntilEvent(ActivityEvent.DESTROY));

    exerciseObservable
        .subscribe(this::setExercise, this::onError);

    /* If the exercise is updated after the initial load, it has been changed elsewhere -
     * e.g. in the ExerciseTypePickerActivity
     */
    exerciseObservable.skip(1).take(1).subscribe(
        ignored -> { exerciseChanged = true; }, this::onError);
  }

  @Override public void onWeightChanged(@Nullable BigDecimal weight) {
    exercise.setWeight(weight);
    exerciseChanged = true;
  }

  @Override public void onRepsChanged(@Nullable Integer reps) {
    exercise.setReps(reps);
    exerciseChanged = true;
  }

  @Override public void onTypeClicked() {
    saveIfChangedThen(() -> view.goToExerciseTypePicker(exercise.id()));
  }

  @Override public void onBack() {
    saveIfChangedThen(() -> view.finish());
  }

  @Override public void onValuesConfirmed() {
    saveIfChangedThen(() -> view.finish());
  }

  private void saveIfChangedThen(Action0 action) {
    if (exerciseChanged) {
      view.showLoading();
      saveExerciseUseCase.setExercise(exercise)
          .perform()
          .toCompletable()
          .subscribe(() -> {
            view.showContent();
            action.call();
          }, this::onError);
    } else {
      action.call();
    }
  }

  private void setExercise(@NonNull Exercise exercise) {
    this.exercise = exercise;
    View view = this.view;
    view.setType(this.exercise.type());
    view.setWeight(this.exercise.weight(), "Kg"); // TODO: 15/06/2016 implement weight units setting
    view.setReps(this.exercise.reps());
    view.showContent();
  }

  @Override public void onError(Throwable t) {
    if (!(t instanceof CancellationException)) {
      logger.error("Error", t);
      view.showError();
    }
  }
}
