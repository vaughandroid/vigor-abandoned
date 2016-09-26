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
import rx.Single;
import rx.Subscription;
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
      // Save a new Exercise & get its ID
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

  private Subscription loadExercise(@NonNull ExerciseId exerciseId) {
    return getExerciseUseCase.setExerciseId(exerciseId)
        .perform()
        .compose(activityLifecycleProvider.bindUntilEvent(ActivityEvent.DESTROY))
        .subscribe(this::setExercise, this::onError);
  }

  @Override public void onTypeClicked() {
    view.goToExerciseTypePicker(exercise.id());
  }

  @Override public void onValuesConfirmed(@Nullable BigDecimal weight, @Nullable Integer reps) {
    view.showLoading();
    exercise.setWeight(weight);
    exercise.setReps(reps);
    saveExerciseUseCase.setExercise(exercise)
        .perform()
        .subscribe(ignored -> view.finish(), this::onError);
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
