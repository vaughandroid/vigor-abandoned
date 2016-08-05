package vaughandroid.vigor.app.exercise.type;

import android.support.annotation.NonNull;
import com.google.common.collect.ImmutableList;
import com.jakewharton.rxbinding.internal.Preconditions;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import vaughandroid.vigor.app.exercise.type.ExerciseTypePickerContract.View;
import vaughandroid.vigor.app.mvp.BasePresenter;
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
public class ExerciseTypePickerPresenter extends BasePresenter<View>
    implements ExerciseTypePickerContract.Presenter {

  private final GetExerciseUseCase getExerciseUseCase;
  private final SaveExerciseUseCase saveExerciseUseCase;
  private final InitExerciseTypesUseCase initExerciseTypesUseCase;
  private final GetExerciseTypesUseCase getExerciseTypesUseCase;

  private Exercise exercise;

  @Inject public ExerciseTypePickerPresenter(ActivityLifecycleProvider activityLifecycleProvider,
      GetExerciseUseCase getExerciseUseCase, SaveExerciseUseCase saveExerciseUseCase,
      InitExerciseTypesUseCase initExerciseTypesUseCase,
      GetExerciseTypesUseCase getExerciseTypesUseCase) {
    super(activityLifecycleProvider);
    this.getExerciseUseCase = getExerciseUseCase;
    this.saveExerciseUseCase = saveExerciseUseCase;
    this.initExerciseTypesUseCase = initExerciseTypesUseCase;
    this.getExerciseTypesUseCase = getExerciseTypesUseCase;
  }

  protected void initView(@NonNull View view) {
    view.showLoading();
  }

  @Override public void init(@NonNull ExerciseId exerciseId) {
    initExerciseTypesUseCase.perform()
        .compose(activityLifecycleProvider.<Boolean>bindToLifecycle().forSingle())
        .subscribe(LogErrorsSubscriber.<Boolean>create());

    Observable<ImmutableList<ExerciseType>> getExerciseTypesObservable =
        getExerciseTypesUseCase.perform().compose(activityLifecycleProvider.bindToLifecycle());
    getExerciseTypesObservable.subscribe(this::onListUpdated, this::onError);

    Observable<Exercise> getExerciseObservable =
        getExerciseUseCase.perform().compose(activityLifecycleProvider.bindToLifecycle());
    getExerciseObservable.doOnNext(this::setExercise);

    Observable.combineLatest(getExerciseTypesObservable, getExerciseObservable,
        (exercise, exerciseTypes) -> exercise != null && exerciseTypes != null)
        .subscribe(this::setReady, this::onError);
  }

  private void setReady(boolean ready) {
    View view = getView();
    if (view != null) {
      if (ready) {
        view.showContent();
      } else {
        view.showLoading();
      }
    }
  }

  private void setExercise(Exercise exercise) {
    this.exercise = exercise;
    String typeName = exercise.type().name();
    getExerciseTypesUseCase.setSearchText(typeName);
    View view = getView();
    if (view != null) {
      view.setSearchText(typeName);
    }
  }

  @Override public void onSearchTextUpdated(@NonNull String text) {
    getExerciseTypesUseCase.setSearchText(text);
  }

  @Override public void onTypePicked(@NonNull ExerciseType exerciseType) {
    Preconditions.checkNotNull(exercise, "exercise == null");
    setReady(false);
    exercise.setType(exerciseType);
    saveExerciseUseCase.setExercise(exercise).perform()
        .compose(activityLifecycleProvider.bindToLifecycle())
        .subscribe(this::onExerciseUpdated, this::onError);
  }

  private void onExerciseUpdated(Exercise exercise) {
    View view = getView();
    if (view != null) {
      view.finish();
    }
  }

  @Override public void onErrorDialogDismissed() {
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

  private void onListUpdated(List<ExerciseType> exerciseTypes) {
    View view = getView();
    if (view != null) {
      view.setExerciseTypes(exerciseTypes);
    }
  }
}
