package vaughandroid.vigor.app.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import javax.inject.Inject;

import rx.Subscription;
import vaughandroid.vigor.app.di.ActivityScope;
import vaughandroid.vigor.domain.exercise.AddExerciseUseCase;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.exercise.GetExerciseUseCase;
import vaughandroid.vigor.domain.rx.BaseSubscriber;
import vaughandroid.vigor.domain.usecase.UseCaseExecutor;

/**
 * MVP presenter implementation for {@link ExerciseContract}
 *
 * @author Chris
 */
@ActivityScope
public class ExercisePresenter implements ExerciseContract.Presenter {

    private final UseCaseExecutor useCaseExecutor;
    private final AddExerciseUseCase addExerciseUseCase;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Exercise exercise;

    @Nullable ExerciseContract.View view;

    @Nullable private Subscription addExerciseSubscription;

    @Inject
    public ExercisePresenter(ExerciseId exerciseId, UseCaseExecutor useCaseExecutor,
            GetExerciseUseCase getExerciseUseCase, AddExerciseUseCase addExerciseUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.addExerciseUseCase = addExerciseUseCase;

        getExerciseUseCase.setId(exerciseId);
        useCaseExecutor.subscribe(getExerciseUseCase, new BaseSubscriber<Exercise>() {
            @Override
            public void onNext(Exercise exercise) {
                super.onNext(exercise);
                setExercise(exercise);
            }
        });
    }

    @Override
    public void resume() {
        initView();
    }

    @Override
    public void destroy() {
        init(null);
        if (addExerciseSubscription != null) {
            addExerciseSubscription.unsubscribe();
            addExerciseSubscription = null;
        }
    }

    @Override
    public void init(@Nullable ExerciseContract.View view) {
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
        }
    }

    @Override
    public void onWeightIncremented() {
        setExercise(exercise.withWeight(exercise.weight().add(BigDecimal.ONE)));
    }

    @Override
    public void onWeightDecremented() {
        setExercise(exercise.withWeight(exercise.weight().subtract(BigDecimal.ONE)));
    }

    @Override
    public void onWeightEntered(@NonNull String weight) {
        try {
            setExercise(exercise.withWeight(new BigDecimal(weight)));
        } catch (NumberFormatException e) {
            logger.warn("Caught invalid weight input: " + weight);
            // Reset valid values.
            updateViewValues();
        }
    }

    @Override
    public void onRepsEntered(@NonNull String reps) {
        try {
            setExercise(exercise.withReps(Integer.valueOf(reps)));
        } catch (NumberFormatException e) {
            logger.warn("Caught invalid reps input: " + reps);
            // Reset valid values.
            updateViewValues();
        }
    }

    @Override
    public void onRepsIncremented() {
        setExercise(exercise.withReps(exercise.reps() + 1));
    }

    @Override
    public void onRepsDecremented() {
        setExercise(exercise.withReps(exercise.reps() - 1));
    }

    @Override
    public void onValuesConfirmed() {
        addExerciseUseCase.setExercise(exercise);
        addExerciseSubscription = useCaseExecutor.subscribe(addExerciseUseCase, new BaseSubscriber<Exercise>() {
            @Override
            public void onNext(Exercise exercise) {
                super.onNext(exercise);
                onSaved(exercise);
            }
        });
    }

    private void setExercise(@NonNull Exercise exercise) {
        this.exercise = exercise;
        updateViewValues();
    }

    private void onSaved(Exercise exercise) {
        this.setExercise(exercise);
        if (view != null) {
            view.finish();
        }
        if (addExerciseSubscription != null) {
            addExerciseSubscription.unsubscribe();
        }
    }
}
