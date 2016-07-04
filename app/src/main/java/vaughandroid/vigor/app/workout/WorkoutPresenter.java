package vaughandroid.vigor.app.workout;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import rx.SingleSubscriber;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.rx.BaseSubscriber;
import vaughandroid.vigor.domain.usecase.UseCaseExecutor;
import vaughandroid.vigor.domain.workout.AddWorkoutUseCase;
import vaughandroid.vigor.domain.workout.GetWorkoutUseCase;
import vaughandroid.vigor.domain.workout.Workout;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * MVP presenter for {@link WorkoutContract}
 *
 * @author Chris
 */
public class WorkoutPresenter implements WorkoutContract.Presenter {

    private final UseCaseExecutor useCaseExecutor;
    private final AddWorkoutUseCase addWorkoutUseCase;
    private final GetWorkoutUseCase getWorkoutUseCase;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Workout workout;

    @Nullable private WorkoutContract.View view;

    @Inject
    public WorkoutPresenter(UseCaseExecutor useCaseExecutor, AddWorkoutUseCase addWorkoutUseCase,
            GetWorkoutUseCase getWorkoutUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.getWorkoutUseCase = getWorkoutUseCase;
        this.addWorkoutUseCase = addWorkoutUseCase;
    }

    @Override
    public void setView(@Nullable WorkoutContract.View view) {
        this.view = view;
        initView();
    }

    private void initView() {
        if (view != null && workout != null) {
            view.setExercises(workout.exercises());
        }
    }

    @Override
    public void setWorkoutId(@NonNull WorkoutId workoutId) {
        if (Objects.equal(workoutId, WorkoutId.UNASSIGNED)) {
            workout = Workout.builder().build();
        } else {
            useCaseExecutor.subscribe(getWorkoutUseCase, new BaseSubscriber<Workout>() {
                @Override
                public void onError(Throwable error) {
                    if (view != null) {
                        view.showError();
                    }
                }

                @Override
                public void onNext(Workout workout) {
                    if (view != null) {
                        view.setExercises(workout.exercises());
                    }
                }
            });
        }
    }

    @Override
    public void onAddExercise() {
        if (workout == null) {
            useCaseExecutor.subscribe(addWorkoutUseCase, new SingleSubscriber<Workout>() {
                @Override
                public void onSuccess(Workout workout) {
                    WorkoutPresenter.this.workout = workout;
                    onAddExercise();
                }

                @Override
                public void onError(Throwable error) {
                    if (view != null) {
                        view.showError();
                    }
                }
            });
        } else if (view != null) {
            // TODO: 19/06/2016 Find a better way of dealing with IDs
            view.openNewExerciseActivity(workout.id());
        }
    }

    @Override
    public void onOpenExercise(@NonNull Exercise exercise) {
        if (view != null) {
            view.openExistingExerciseActivity(workout.id(), exercise.id());
        }
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
}
