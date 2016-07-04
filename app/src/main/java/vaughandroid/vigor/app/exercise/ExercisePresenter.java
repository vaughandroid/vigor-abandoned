package vaughandroid.vigor.app.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import javax.inject.Inject;

import rx.SingleSubscriber;
import vaughandroid.vigor.app.di.ActivityScope;
import vaughandroid.vigor.domain.exercise.AddExerciseUseCase;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.exercise.GetExerciseUseCase;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.usecase.UseCaseExecutor;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * MVP presenter implementation for {@link ExerciseContract}
 *
 * @author Chris
 */
@ActivityScope
public class ExercisePresenter implements ExerciseContract.Presenter {

    private final UseCaseExecutor useCaseExecutor;
    private final AddExerciseUseCase addExerciseUseCase;
    private final GetExerciseUseCase getExerciseUseCase;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Exercise exercise;
    @Nullable ExerciseContract.View view;

    @Inject
    public ExercisePresenter(UseCaseExecutor useCaseExecutor, AddExerciseUseCase addExerciseUseCase,
            GetExerciseUseCase getExerciseUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.addExerciseUseCase = addExerciseUseCase;
        this.getExerciseUseCase = getExerciseUseCase;
    }

    @Override
    public void init(@NonNull WorkoutId workoutId, @NonNull ExerciseId exerciseId) {
        if (Objects.equal(exerciseId, ExerciseId.UNASSIGNED)) {
            setExercise(Exercise.builder().workoutId(workoutId).build());
        } else {
            getExerciseUseCase.setExerciseId(exerciseId);
            useCaseExecutor.subscribe(getExerciseUseCase, new SingleSubscriber<Exercise>() {
                @Override
                public void onSuccess(Exercise exercise) {
                    setExercise(exercise);
                }

                @Override
                public void onError(Throwable error) {
                    if (view != null) {
                        view.showError();
                    }
                }
            });
        }
    }

    @Override
    public void setView(@Nullable ExerciseContract.View view) {
        this.view = view;
        initView();
    }

    private void initView() {
        updateViewValues();
    }

    private void updateViewValues() {
        if (view != null && exercise != null) {
            view.setWeight(exercise.weight());
            view.setWeightUnits("Kg"); // TODO: 15/06/2016 implement weight units setting
            view.setReps(exercise.reps());
            view.showContent();
        }
    }

    @Override
    public void onTypeClicked() {
        if (view != null) {
            view.openTypePicker(exercise.type());
        }
    }

    @Override
    public void onWeightEntered(@Nullable BigDecimal weight) {
        setExercise(exercise.withWeight(weight));
    }

    @Override
    public void onRepsEntered(@Nullable Integer reps) {
        try {
            setExercise(exercise.withReps(reps));
        } catch (NumberFormatException e) {
            logger.warn("Caught invalid reps input: " + reps);
            // Reset valid values.
            updateViewValues();
        }
    }

    @Override
    public void onValuesConfirmed() {
        addExerciseUseCase.setExercise(exercise);
        useCaseExecutor.subscribe(addExerciseUseCase, new SingleSubscriber<Exercise>() {
            @Override
            public void onSuccess(Exercise exercise) {
                onSaved(exercise);
            }

            @Override
            public void onError(Throwable error) {
                if (view != null) {
                    view.showError();
                }
            }
        });
    }

    @Override
    public void onTypePicked(@NonNull ExerciseType typeFromResult) {
        exercise.withType(typeFromResult);
    }

    @Override
    public void resume() {
        initView();
    }

    @Override
    public void destroy() {
        setView(null);
        useCaseExecutor.unsubscribe();
    }

    private void setExercise(@NonNull Exercise exercise) {
        this.exercise = exercise;
        updateViewValues();
    }

    private void onSaved(Exercise exercise) {
        this.setExercise(exercise);
        if (view != null) {
            view.onSaved();
        }
    }
}
