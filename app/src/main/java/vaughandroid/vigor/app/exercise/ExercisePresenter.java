package vaughandroid.vigor.app.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.common.base.Objects;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import java.math.BigDecimal;
import javax.inject.Inject;
import vaughandroid.vigor.app.di.ActivityScope;
import vaughandroid.vigor.app.exercise.ExerciseContract.View;
import vaughandroid.vigor.app.mvp.BasePresenter;
import vaughandroid.vigor.domain.exercise.SaveExerciseUseCase;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.exercise.GetExerciseUseCase;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * MVP presenter implementation for {@link ExerciseContract}
 *
 * @author Chris
 */
@ActivityScope public class ExercisePresenter extends BasePresenter<View>
    implements ExerciseContract.Presenter {

  private final SaveExerciseUseCase saveExerciseUseCase;
  private final GetExerciseUseCase getExerciseUseCase;

  private Exercise exercise;

  @Inject public ExercisePresenter(ActivityLifecycleProvider activityLifecycleProvider,
      SaveExerciseUseCase saveExerciseUseCase, GetExerciseUseCase getExerciseUseCase) {
    super(activityLifecycleProvider);
    this.saveExerciseUseCase = saveExerciseUseCase;
    this.getExerciseUseCase = getExerciseUseCase;
  }

  @Override public void init(@NonNull WorkoutId workoutId, @NonNull ExerciseId exerciseId) {
    if (Objects.equal(exerciseId, ExerciseId.UNASSIGNED)) {
      setExercise(Exercise.builder().workoutId(workoutId).build());
    } else {
      getExerciseUseCase.setExerciseId(exerciseId);
      getExerciseUseCase.perform()
          .compose(activityLifecycleProvider.bindToLifecycle())
          .subscribe(ExercisePresenter.this::setExercise, ExercisePresenter.this::onError);
    }
  }

  @Override protected void initView(@NonNull View view) {
    updateViewValues();
  }

  private void updateViewValues() {
    View view = getView();
    if (view != null && exercise != null) {
      view.setType(exercise.type());
      view.setWeight(exercise.weight(), "Kg"); // TODO: 15/06/2016 implement weight units setting
      view.setReps(exercise.reps());
    }
  }

  @Override public void onTypeClicked() {
    View view = getView();
    if (view != null) {
      view.goToExerciseTypePicker(exercise.id());
    }
  }

  @Override public void onValuesConfirmed(@Nullable BigDecimal weight, @Nullable Integer reps) {
    setExercise(exercise.withWeight(weight).withReps(reps));

    saveExerciseUseCase.setExercise(exercise);
    saveExerciseUseCase.perform()
        .subscribe(ExercisePresenter.this::onSaved, ExercisePresenter.this::onError);
  }

  private void setExercise(@NonNull Exercise exercise) {
    this.exercise = exercise;
    updateViewValues();
  }

  private void onSaved(@NonNull Exercise exercise) {
    this.setExercise(exercise);
    View view = getView();
    if (view != null) {
      view.finish();
    }
  }

  @Override public void onError(Throwable t) {
    logger.error("Error", t);
    View view = getView();
    if (view != null) {
      view.showError();
    }
  }
}
