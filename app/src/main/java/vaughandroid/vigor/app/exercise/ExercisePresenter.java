package vaughandroid.vigor.app.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.trello.rxlifecycle.ActivityLifecycleProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import javax.inject.Inject;

import vaughandroid.vigor.app.di.ActivityScope;
import vaughandroid.vigor.app.exercise.ExerciseContract.View;
import vaughandroid.vigor.app.mvp.BasePresenter;
import vaughandroid.vigor.domain.exercise.AddExerciseUseCase;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.exercise.GetExerciseUseCase;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * MVP presenter implementation for {@link ExerciseContract}
 *
 * @author Chris
 */
@ActivityScope
public class ExercisePresenter extends BasePresenter<View>
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
        super(activityLifecycleProvider);
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
                    .compose(activityLifecycleProvider.bindToLifecycle())
                    .subscribe(ExercisePresenter.this::setExercise, ExercisePresenter.this::showError);
        }
    }

    private void showError(Throwable throwable) {
        View view = getView();
        if (view != null) {
            view.showError();
        }
    }

    @Override
    protected void initView(@NonNull View view) {
        updateViewValues();
    }

    private void updateViewValues() {
        View view = getView();
        if (view != null && exercise != null) {
            view.setType(exercise.type());
            view.setWeight(exercise.weight());
            view.setWeightUnits("Kg"); // TODO: 15/06/2016 implement weight units setting
            view.setReps(exercise.reps());
            view.showContent();
        }
    }

    @Override
    public void onTypeClicked() {
        View view = getView();
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
        addExerciseUseCase.createObservable()
                .subscribe(ExercisePresenter.this::onSaved, ExercisePresenter.this::showError);
    }

    @Override
    public void onTypePicked(@NonNull ExerciseType typeFromResult) {
        setExercise(exercise.withType(typeFromResult));
    }

    private void setExercise(@NonNull Exercise exercise) {
        this.exercise = exercise;
        updateViewValues();
    }

    private void onSaved(@NonNull Exercise exercise) {
        this.setExercise(exercise);
        View view = getView();
        if (view != null) {
            view.onSaved(exercise);
        }
    }
}
