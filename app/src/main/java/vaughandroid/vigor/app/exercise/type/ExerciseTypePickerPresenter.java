package vaughandroid.vigor.app.exercise.type;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.exercise.type.GetAllExerciseTypesUseCase;
import vaughandroid.vigor.domain.usecase.UseCaseExecutor;
import vaughandroid.vigor.utils.Preconditions;

/**
 * MVP Presenter for the {@link ExerciseTypePickerActivity}
 *
 * @author chris.vaughan@laterooms.com
 */
public class ExerciseTypePickerPresenter implements ExerciseTypePickerContract.Presenter {

    private final UseCaseExecutor useCaseExecutor;
    private final GetAllExerciseTypesUseCase getAllExerciseTypesUseCase;

    @Nullable private ExerciseTypePickerContract.View view;

    @NonNull private ExerciseType exerciseType = ExerciseType.UNSET;

    @Inject
    public ExerciseTypePickerPresenter(UseCaseExecutor useCaseExecutor, GetAllExerciseTypesUseCase getAllExerciseTypesUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.getAllExerciseTypesUseCase = getAllExerciseTypesUseCase;
    }

    @Override
    public void setView(@NonNull ExerciseTypePickerContract.View view) {
        this.view = view;

        // XXX need to manage view subscriptions, or could use RxLifecycle?
        view.typePicked()
                .subscribe(exerciseType -> {
                    this.exerciseType = exerciseType;
                    if (this.view != null) {
                        this.view.returnPickedType(exerciseType);
                    }
                });
        view.searchText()
                .subscribe(text -> {
                    getAllExerciseTypesUseCase.setSearchText(text);
                    useCaseExecutor.subscribe(getAllExerciseTypesUseCase);
                });
        initView();
    }

    private void initView() {
        Preconditions.checkState(view != null, "view not set");
        view.setSearchText(exerciseType.name());
    }

    @Override
    public void init(@NonNull ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
        initView();
        useCaseExecutor.subscribe(getAllExerciseTypesUseCase,
                (exerciseTypes) -> {
                    if (view != null) {
                        view.setListEntries(exerciseTypes);
                    }
                });
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {
        useCaseExecutor.unsubscribe();
    }
}
