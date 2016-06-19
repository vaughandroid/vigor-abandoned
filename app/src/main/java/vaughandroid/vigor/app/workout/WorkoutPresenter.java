package vaughandroid.vigor.app.workout;

import android.support.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
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
    private final List<Subscription> subscriptions = new ArrayList<>();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Workout workout;

    @Nullable private WorkoutContract.View view;

    @Inject
    public WorkoutPresenter(UseCaseExecutor useCaseExecutor, AddWorkoutUseCase addWorkoutUseCase, GetWorkoutUseCase getWorkoutUseCase) {
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
    public void setWorkoutId(@Nullable WorkoutId workoutId) {
        if (workoutId != null) {
            subscriptions.add(useCaseExecutor.subscribe(getWorkoutUseCase, new SingleSubscriber<Workout>() {
                @Override
                public void onSuccess(Workout workout) {
                    if (view != null) {
                        view.setExercises(workout.exercises());
                    }
                }

                @Override
                public void onError(Throwable error) {
                    if (view != null) {
                        view.showError();
                    }
                }
            }));
        } else {
            workout = Workout.builder().build();
        }
    }

    @Override
    public void onAddExercise() {
        if (view != null) {
            view.openNewExerciseActivity();
        }
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
}
