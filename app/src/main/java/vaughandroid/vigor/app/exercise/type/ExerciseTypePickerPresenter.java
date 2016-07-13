package vaughandroid.vigor.app.exercise.type;

import android.support.annotation.NonNull;

import com.google.common.base.Preconditions;
import com.trello.rxlifecycle.ActivityLifecycleProvider;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import vaughandroid.vigor.app.exercise.type.ExerciseTypePickerContract.View;
import vaughandroid.vigor.app.mvp.BasePresenter;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.exercise.type.GetExerciseTypesUseCase;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;

/**
 * MVP Presenter for the {@link ExerciseTypePickerActivity}
 *
 * @author Chris
 */
public class ExerciseTypePickerPresenter extends BasePresenter<View> implements ExerciseTypePickerContract.Presenter {

    private final GetExerciseTypesUseCase getExerciseTypesUseCase;

    @NonNull private ExerciseType exerciseType = ExerciseType.UNSET;

    @Inject
    public ExerciseTypePickerPresenter(ActivityLifecycleProvider activityLifecycleProvider,
            SchedulingPolicy schedulingPolicy, GetExerciseTypesUseCase getExerciseTypesUseCase) {
        super(activityLifecycleProvider, schedulingPolicy);
        this.getExerciseTypesUseCase = getExerciseTypesUseCase;
    }

    protected void initView(@NonNull View view) {
        view.setSearchText(exerciseType.name());
    }

    @Override
    public void init(@NonNull ExerciseType exerciseType) {
        View view = getView();
        Preconditions.checkState(view != null, "view == null");

        this.exerciseType = exerciseType;
        initView(view);

        Observable.just(this.exerciseType)
                .map(ExerciseType::name)
                .mergeWith(view.searchText())
                .subscribe(getExerciseTypesUseCase::setSearchText);

        getExerciseTypesUseCase.createObservable()
                .compose(useCaseTransformer())
                .subscribe(this::onListUpdated);

        view.typePicked()
                .compose(uiTransformer())
                .subscribe(this::onTypePicked);
    }

    private void onTypePicked(@NonNull ExerciseType exerciseType) {
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
}
