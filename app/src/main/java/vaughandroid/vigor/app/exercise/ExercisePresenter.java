package vaughandroid.vigor.app.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import vaughandroid.vigor.app.di.ActivityScope;
import vaughandroid.vigor.domain.exercise.AddExerciseUseCase;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.SavedExercise;
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

    @NonNull private Exercise exercise = Exercise.builder().build();
    @Nullable private SavedExercise savedExercise;

    @Nullable ExerciseContract.View view;

    @Nullable private Subscription addExerciseSubscription;

    @Inject
    public ExercisePresenter(UseCaseExecutor useCaseExecutor, AddExerciseUseCase addExerciseUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.addExerciseUseCase = addExerciseUseCase;
    }

    @Override
    public void resume() {
        initView();
    }

    @Override
    public void destroy() {
        setView(null);
        addExerciseSubscription.unsubscribe();
        addExerciseSubscription = null;
    }

    @Override
    public void setView(@Nullable ExerciseContract.View view) {
        this.view = view;
        initView();
    }

    private void initView() {
        if (view != null) {
            view.setWeight(exercise.weight());
            view.setReps(exercise.reps());
        }
    }

    @Override
    public void onWeightChanged(@NonNull BigDecimal weight) {
        exercise = exercise.withWeight(weight);
    }

    @Override
    public void onRepsChanged(int reps) {
        exercise = exercise.withReps(reps);
    }

    @Override
    public void onValuesConfirmed() {
        addExerciseUseCase.setData(exercise);
        addExerciseSubscription = useCaseExecutor.subscribe(addExerciseUseCase, new ExerciseSubscriber());
    }

    private void onSaved(SavedExercise savedExercise) {
        this.savedExercise = savedExercise;
        if (view != null) {
            view.finish(savedExercise);
        }
        if (addExerciseSubscription != null) {
            addExerciseSubscription.unsubscribe();
        }
    }

    private class ExerciseSubscriber extends Subscriber<SavedExercise> {

        @Override
        public void onCompleted() {
            logger.debug("onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            logger.error("onError", e);
        }

        @Override
        public void onNext(SavedExercise savedExercise) {
            logger.debug("onNext");
            onSaved(savedExercise);
        }
    }
}
