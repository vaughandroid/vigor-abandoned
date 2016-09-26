package vaughandroid.vigor.app.exercise.type;

import android.support.annotation.NonNull;
import com.google.common.collect.ImmutableList;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import vaughandroid.vigor.app.exercise.type.ExerciseTypePickerContract.View;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.exercise.GetExerciseUseCase;
import vaughandroid.vigor.domain.exercise.SaveExerciseUseCase;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.exercise.type.GetExerciseTypesUseCase;
import vaughandroid.vigor.domain.exercise.type.InitExerciseTypesUseCase;
import vaughandroid.vigor.domain.rx.LogErrorsSubscriber;

/**
 * MVP Presenter for the {@link ExerciseTypePickerActivity}
 *
 * @author Chris
 */
public class ExerciseTypePickerPresenter implements ExerciseTypePickerContract.Presenter {

  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final ActivityLifecycleProvider activityLifecycleProvider;
  private final GetExerciseUseCase getExerciseUseCase;
  private final SaveExerciseUseCase saveExerciseUseCase;
  private final InitExerciseTypesUseCase initExerciseTypesUseCase;
  private final GetExerciseTypesUseCase getExerciseTypesUseCase;

  private View view;
  private Exercise exercise;

  @Inject public ExerciseTypePickerPresenter(ActivityLifecycleProvider activityLifecycleProvider,
      GetExerciseUseCase getExerciseUseCase, SaveExerciseUseCase saveExerciseUseCase,
      InitExerciseTypesUseCase initExerciseTypesUseCase,
      GetExerciseTypesUseCase getExerciseTypesUseCase) {
    this.activityLifecycleProvider = activityLifecycleProvider;
    this.getExerciseUseCase = getExerciseUseCase;
    this.saveExerciseUseCase = saveExerciseUseCase;
    this.initExerciseTypesUseCase = initExerciseTypesUseCase;
    this.getExerciseTypesUseCase = getExerciseTypesUseCase;
  }

  @Override public void init(@NonNull View view, @NonNull ExerciseId exerciseId) {
    this.view = view;
    view.showLoading();

    initExerciseTypesUseCase.perform()
        .compose(activityLifecycleProvider.<Boolean>bindToLifecycle().forSingle())
        .subscribe(LogErrorsSubscriber.<Boolean>create());

    Observable<ImmutableList<ExerciseType>> getExerciseTypesObservable = getExerciseTypesUseCase
        .perform()
        .compose(activityLifecycleProvider.bindToLifecycle());
    getExerciseTypesObservable.subscribe(this::onListUpdated, this::onError);

    Observable<Exercise> getExerciseObservable = getExerciseUseCase.setExerciseId(exerciseId)
        .perform()
        .compose(activityLifecycleProvider.bindToLifecycle())
        .doOnNext(this::setExercise);

    Observable.combineLatest(
        getExerciseTypesObservable,
        getExerciseObservable,
        (exerciseTypes, exercise) -> true)
        .subscribe(this::setReady, this::onError);
  }

  private void setReady(boolean ready) {
    if (ready) {
      view.showContent();
    } else {
      view.showLoading();
    }
  }

  private void setExercise(Exercise exercise) {
    this.exercise = exercise;
    String typeName = exercise.type().name();
    getExerciseTypesUseCase.setSearchText(typeName);
    view.setSearchText(typeName);
  }

  @Override public void onSearchTextUpdated(@NonNull String text) {
    getExerciseTypesUseCase.setSearchText(text);
  }

  @Override public void onTypePicked(@NonNull ExerciseType exerciseType) {
    setReady(false);
    exercise.setType(exerciseType);
    saveExerciseUseCase.setExercise(exercise)
        .perform()
        .compose(activityLifecycleProvider.<Exercise>bindToLifecycle().forSingle())
        .subscribe(this::onExerciseUpdated, this::onError);
  }

  private void onExerciseUpdated(Exercise exercise) {
    view.finish();
  }

  @Override public void onError(Throwable t) {
    logger.error("Error", t);
    view.showError();
  }

  private void onListUpdated(List<ExerciseType> exerciseTypes) {
    view.setExerciseTypes(exerciseTypes);
  }
}
