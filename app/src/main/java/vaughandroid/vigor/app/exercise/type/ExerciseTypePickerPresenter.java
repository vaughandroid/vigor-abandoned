package vaughandroid.vigor.app.exercise.type;

import android.support.annotation.NonNull;

import com.trello.rxlifecycle.ActivityLifecycleProvider;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import vaughandroid.vigor.app.exercise.type.ExerciseTypePickerContract.View;
import vaughandroid.vigor.app.mvp.BasePresenter;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.exercise.type.GetExerciseTypesUseCase;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;
import vaughandroid.vigor.utils.Preconditions;

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

        view.searchText()
                .compose(uiTransformer())
                .subscribe();
        view.typePicked()
                .compose(uiTransformer())
                .subscribe();
    }

    @Override
    public void init(@NonNull ExerciseType exerciseType) {
        View view = getView();
        Preconditions.checkState(view != null, "view == null");

        this.exerciseType = exerciseType;
        initView(view);

        Observable.just(exerciseType)
                .map(ExerciseType::name)
                .mergeWith(view.searchText())
                .subscribe(getExerciseTypesUseCase::setSearchText);

        getExerciseTypesUseCase.createObservable()
                .compose(useCaseTransformer())
                .subscribe(ExerciseTypePickerPresenter.this::updateView);
    }

    private void updateView(List<ExerciseType> exerciseTypes) {
        View view1 = getView();
        if (view1 != null) {
            view1.setListEntries(exerciseTypes);
        }
    }

}
