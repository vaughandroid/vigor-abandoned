package vaughandroid.vigor.app.di;

import dagger.Subcomponent;
import vaughandroid.vigor.app.exercise.ExerciseComponent;
import vaughandroid.vigor.app.exercise.ExerciseModule;
import vaughandroid.vigor.app.workout.WorkoutActivity;
import vaughandroid.vigor.app.workout.WorkoutModule;

/**
 * Injector of {@link ActivityScope}d instances.
 *
 * @author Chris
 */
@Subcomponent(modules = {
        WorkoutModule.class,
})
@ActivityScope
public interface ActivityComponent {

    ExerciseComponent exerciseComponent(ExerciseModule exerciseModule);

    void inject(WorkoutActivity activity);
}
