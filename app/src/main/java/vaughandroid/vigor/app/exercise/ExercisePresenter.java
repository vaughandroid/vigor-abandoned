package vaughandroid.vigor.app.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.math.BigDecimal;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import vaughandroid.vigor.app.di.ActivityScope;
import vaughandroid.vigor.app.logging.Logger;
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
    private final Logger logger;

    @NonNull private Exercise exercise = Exercise.builder().build();

    @Nullable ExerciseContract.View view;

    @Nullable private Subscription addExerciseSubscription;

    @Inject
    public ExercisePresenter(UseCaseExecutor useCaseExecutor, AddExerciseUseCase addExerciseUseCase, Logger logger) {
        this.useCaseExecutor = useCaseExecutor;
        this.addExerciseUseCase = addExerciseUseCase;
        this.logger = logger;
    }

    @Override
    public void resume() {
        initView();
    }

    @Override
    public void destroy() {
        setView(null);
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

    private class ExerciseSubscriber extends Subscriber<SavedExercise> {
        @Override
        public void onCompleted() {
            logger.d("onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            logger.e(e, "onError");
        }

        @Override
        public void onNext(SavedExercise savedExercise) {
            logger.d("onNext");
        }
    }
}
