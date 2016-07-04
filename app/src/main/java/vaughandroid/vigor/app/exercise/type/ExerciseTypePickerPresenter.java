package vaughandroid.vigor.app.exercise.type;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.exercise.type.GetExerciseListUseCase;
import vaughandroid.vigor.domain.rx.BaseSubscriber;
import vaughandroid.vigor.domain.usecase.UseCaseExecutor;

/**
 * MVP Presenter for the {@link ExerciseTypePickerActivity}
 *
 * @author chris.vaughan@laterooms.com
 */
public class ExerciseTypePickerPresenter implements ExerciseTypePickerContract.Presenter {

    private final UseCaseExecutor useCaseExecutor;
    private final GetExerciseListUseCase getExerciseListUseCase;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Nullable private ExerciseTypePickerContract.View view;

    @NonNull private ExerciseType exerciseType = ExerciseType.UNSET;

    @Inject
    public ExerciseTypePickerPresenter(UseCaseExecutor useCaseExecutor, GetExerciseListUseCase getExerciseListUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.getExerciseListUseCase = getExerciseListUseCase;
    }

    @Override
    public void setView(@Nullable ExerciseTypePickerContract.View view) {
        this.view = view;
        initView();
    }

    private void initView() {
        if (view != null) {
            view.setSearchText(exerciseType.name());
        }
    }

    @Override
    public void init(@NonNull ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
        initView();
        useCaseExecutor.subscribe(getExerciseListUseCase,
                new BaseSubscriber<ImmutableList<ExerciseType>>() {
                    @Override
                    public void onError(Throwable e) {
                        logger.error("", e);
                    }

                    @Override
                    public void onNext(ImmutableList<ExerciseType> exerciseTypes) {
                        if (view != null) {
                            view.setListEntries(exerciseTypes);
                        }
                    }
                });
    }

    @Override
    public void onTextEntered(@NonNull String text) {

    }

    @Override
    public void onTextConfirmed() {

    }

    @Override
    public void onTypePickedFromList(@NonNull ExerciseType type) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {
        useCaseExecutor.unsubscribe();
    }
}
