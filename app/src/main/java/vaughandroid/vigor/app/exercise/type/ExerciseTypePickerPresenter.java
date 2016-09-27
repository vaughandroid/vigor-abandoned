package vaughandroid.vigor.app.exercise.type;

import android.support.annotation.NonNull;
import com.google.common.collect.ImmutableList;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import java.util.concurrent.CancellationException;
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

    initExerciseTypesUseCase.getSingle()
        .compose(activityLifecycleProvider.<Boolean>bindUntilEvent(ActivityEvent.DESTROY).forSingle())
        .subscribe(LogErrorsSubscriber.<Boolean>create());

    Observable<ImmutableList<ExerciseType>> getExerciseTypesObservable = getExerciseTypesUseCase
        .getObservable()
        .compose(activityLifecycleProvider.bindUntilEvent(ActivityEvent.DESTROY));
    getExerciseTypesObservable.subscribe(view::setExerciseTypes, this::onError);

    Observable<Exercise> getExerciseObservable = getExerciseUseCase.setExerciseId(exerciseId)
        .getObservable()
        .compose(activityLifecycleProvider.bindUntilEvent(ActivityEvent.DESTROY))
        .doOnNext(this::setExercise);

    Observable.combineLatest(
        getExerciseTypesObservable,
        getExerciseObservable,
        (exerciseTypes, exercise) -> null)
        .subscribe(ignored -> view.showContent(), this::onError);
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
    view.showLoading();
    exercise.setType(exerciseType);
    saveExerciseUseCase.setExercise(exercise)
        .getSingle()
        .compose(activityLifecycleProvider.<Exercise>bindUntilEvent(ActivityEvent.DESTROY).forSingle())
        .subscribe(ignored -> view.finish(), this::onError);
  }

  @Override public void onError(Throwable t) {
    if (!(t instanceof CancellationException)) {
      logger.error("Error", t);
      view.showError();
    }
  }
}
