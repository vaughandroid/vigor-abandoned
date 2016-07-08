package vaughandroid.vigor.app.workout;

import android.support.annotation.NonNull;

import com.google.common.base.Objects;
import com.trello.rxlifecycle.ActivityLifecycleProvider;

import javax.inject.Inject;

import vaughandroid.vigor.app.mvp.BasePresenter;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;
import vaughandroid.vigor.domain.workout.AddWorkoutUseCase;
import vaughandroid.vigor.domain.workout.GetWorkoutUseCase;
import vaughandroid.vigor.domain.workout.Workout;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * MVP presenter for {@link WorkoutContract}
 *
 * @author Chris
 */
public class WorkoutPresenter extends BasePresenter<WorkoutContract.View>
        implements WorkoutContract.Presenter {

    private final AddWorkoutUseCase addWorkoutUseCase;
    private final GetWorkoutUseCase getWorkoutUseCase;

    private Workout workout;

    @Inject
    public WorkoutPresenter(ActivityLifecycleProvider activityLifecycleProvider,
            SchedulingPolicy domainSchedulingPolicy, AddWorkoutUseCase addWorkoutUseCase,
            GetWorkoutUseCase getWorkoutUseCase) {
        super(activityLifecycleProvider, domainSchedulingPolicy);
        this.getWorkoutUseCase = getWorkoutUseCase;
        this.addWorkoutUseCase = addWorkoutUseCase;
    }

    @Override
    protected void initView(@NonNull WorkoutContract.View view) {
        if (workout != null) {
            view.setExercises(workout.exercises());
        }
    }

    @Override
    public void setWorkoutId(@NonNull WorkoutId workoutId) {
        if (Objects.equal(workoutId, WorkoutId.UNASSIGNED)) {
            workout = Workout.builder().build();
        } else {
            getWorkoutUseCase.createObservable()
                    .compose(useCaseTransformer())
                    .subscribe(this::setWorkout, this::showError);
        }
    }


    @Override
    public void onAddExercise() {
        if (workout == null) {
            addWorkoutUseCase.createObservable()
                    .compose(useCaseTransformer())
                    .subscribe(this::setWorkout, this::showError);
        } else if (getView() != null) {
            // TODO: 19/06/2016 Find a better way of dealing with IDs
            getView().openNewExerciseActivity(workout.id());
        }
    }

    private void setWorkout(Workout workout) {
        this.workout = workout;
        if (getView() != null) {
            getView().setExercises(workout.exercises());
        }
    }

    private void showError(Throwable t) {
        if (getView() != null) {
            getView().showError();
        }
    }

    @Override
    public void onOpenExercise(@NonNull Exercise exercise) {
        if (getView() != null) {
            getView().openExistingExerciseActivity(workout.id(), exercise.id());
        }
    }
}
