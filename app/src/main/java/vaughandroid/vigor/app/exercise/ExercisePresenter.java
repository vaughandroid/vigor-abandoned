package vaughandroid.vigor.app.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import vaughandroid.vigor.app.di.ActivityScope;
import vaughandroid.vigor.domain.exercise.AddExerciseUseCase;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.exercise.GetExerciseUseCase;
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
    private final GetExerciseUseCase getExerciseUseCase;
    private final List<Subscription> subscriptions = new ArrayList<>();

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
    public void setExerciseId(@Nullable ExerciseId exerciseId) {
        if (exerciseId != null) {
            getExerciseUseCase.setExerciseId(exerciseId);
            subscriptions.add(useCaseExecutor.subscribe(getExerciseUseCase, new SingleSubscriber<Exercise>() {
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
            }));
        } else {
            setExercise(Exercise.builder().build());
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
        subscriptions.add(useCaseExecutor.subscribe(addExerciseUseCase, new SingleSubscriber<Exercise>() {
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
        }));
    }

    @Override
    public void resume() {
        initView();
    }

    @Override
    public void destroy() {
        setView(null);
        for (Subscription subscription : subscriptions) {
            subscription.unsubscribe();
        }
        subscriptions.clear();
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
