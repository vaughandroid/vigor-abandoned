package vaughandroid.vigor.app.exercise.type;

import android.support.annotation.NonNull;
import com.google.common.base.Preconditions;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import vaughandroid.vigor.app.exercise.ExerciseContract;
import vaughandroid.vigor.app.exercise.type.ExerciseTypePickerContract.View;
import vaughandroid.vigor.app.mvp.BasePresenter;
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

  private final InitExerciseTypesUseCase initExerciseTypesUseCase;
  private final GetExerciseTypesUseCase getExerciseTypesUseCase;

  @NonNull private ExerciseType exerciseType = ExerciseType.UNSET;

  @Inject public ExerciseTypePickerPresenter(ActivityLifecycleProvider activityLifecycleProvider,
      InitExerciseTypesUseCase initExerciseTypesUseCase,
      GetExerciseTypesUseCase getExerciseTypesUseCase) {
    super(activityLifecycleProvider);
    this.initExerciseTypesUseCase = initExerciseTypesUseCase;
    this.getExerciseTypesUseCase = getExerciseTypesUseCase;
  }

  protected void initView(@NonNull View view) {
    view.setSearchText(exerciseType.name());
  }

  @Override public void init(@NonNull ExerciseType exerciseType) {
    View view = getView();
    Preconditions.checkState(view != null, "view == null");

    this.exerciseType = exerciseType;
    initView(view);

    initExerciseTypesUseCase.perform()
        .compose(activityLifecycleProvider.<Boolean>bindToLifecycle().forSingle())
        .subscribe(LogErrorsSubscriber.<Boolean>create());

    getExerciseTypesUseCase.setSearchText(exerciseType.name());

    getExerciseTypesUseCase.perform()
        .compose(activityLifecycleProvider.bindToLifecycle())
        .subscribe(this::onListUpdated, this::showError);
  }

  @Override public void onViewError(Throwable t) {

  }

  @Override public void onErrorDialogDismissed() {
    View view = getView();
    if (view != null) {
      view.returnCancelled();
    }
  }

  @Override public void onSearchTextUpdated(@NonNull String text) {
    getExerciseTypesUseCase.setSearchText(text);
  }

  @Override public void onTypePicked(@NonNull ExerciseType exerciseType) {
    View view = getView();
    if (view != null) {
      view.returnPickedType(exerciseType);
    }
  }

  private void onListUpdated(List<ExerciseType> exerciseTypes) {
    View view = getView();
    if (view != null) {
      view.setListEntries(exerciseTypes);
    }
  }

  private void showError(Throwable t) {
    ExerciseTypePickerContract.View view = getView();
    if (view != null) {
      view.showError();
    }
  }
}
