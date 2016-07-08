package vaughandroid.vigor.app.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle.ActivityLifecycleProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import javax.inject.Inject;

import rx.functions.Action1;
import vaughandroid.vigor.app.di.ActivityScope;
import vaughandroid.vigor.app.mvp.BasePresenter;
import vaughandroid.vigor.domain.exercise.AddExerciseUseCase;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.exercise.GetExerciseUseCase;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;
import vaughandroid.vigor.domain.workout.WorkoutId;
import vaughandroid.vigor.utils.Objects;

/**
 * MVP presenter implementation for {@link ExerciseContract}
 *
 * @author Chris
 */
@ActivityScope
public class ExercisePresenter extends BasePresenter<ExerciseContract.View>
        implements ExerciseContract.Presenter {

    private final AddExerciseUseCase addExerciseUseCase;
    private final GetExerciseUseCase getExerciseUseCase;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Exercise exercise;

    @Inject
    public ExercisePresenter(
            ActivityLifecycleProvider activityLifecycleProvider,
            SchedulingPolicy domainSchedulingPolicy,
            AddExerciseUseCase addExerciseUseCase,
            GetExerciseUseCase getExerciseUseCase) {
        super(activityLifecycleProvider, domainSchedulingPolicy);
        this.addExerciseUseCase = addExerciseUseCase;
        this.getExerciseUseCase = getExerciseUseCase;
    }

    @Override
    public void init(@NonNull WorkoutId workoutId, @NonNull ExerciseId exerciseId) {
        if (Objects.equal(exerciseId, ExerciseId.UNASSIGNED)) {
            setExercise(Exercise.builder().workoutId(workoutId).build());
        } else {
            getExerciseUseCase.setExerciseId(exerciseId);
            getExerciseUseCase.createObservable()
                    .compose(useCaseTransformer())
                    .subscribe(new Action1<Exercise>() {
                        @Override
                        public void call(Exercise exercise1) {
                            ExercisePresenter.this.setExercise(exercise1);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            ExercisePresenter.this.showError(throwable);
                        }
                    });
        }
    }

    private void showError(Throwable throwable) {
        if (getView() != null) {
            getView().showError();
        }
    }

    @Override
    protected void initView(@NonNull ExerciseContract.View view) {
        updateViewValues();
    }

    private void updateViewValues() {
        if (getView() != null && exercise != null) {
            getView().setWeight(exercise.weight());
            getView().setWeightUnits("Kg"); // TODO: 15/06/2016 implement weight units setting
            getView().setReps(exercise.reps());
            getView().showContent();
        }
    }

    @Override
    public void onTypeClicked() {
        if (getView() != null) {
            getView().openTypePicker(exercise.type());
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
        addExerciseUseCase.createObservable()
                .subscribe(new Action1<Exercise>() {
                    @Override
                    public void call(Exercise exercise1) {
                        ExercisePresenter.this.onSaved(exercise1);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ExercisePresenter.this.showError(throwable);
                    }
                });
    }

    @Override
    public void onTypePicked(@NonNull ExerciseType typeFromResult) {
        exercise.withType(typeFromResult);
    }

    private void setExercise(@NonNull Exercise exercise) {
        this.exercise = exercise;
        updateViewValues();
    }

    private void onSaved(Exercise exercise) {
        this.setExercise(exercise);
        if (getView() != null) {
            getView().onSaved();
        }
    }
}
