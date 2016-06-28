package vaughandroid.vigor.app.di;

import dagger.Subcomponent;
import vaughandroid.vigor.app.exercise.ExerciseActivity;
import vaughandroid.vigor.app.exercise.ExerciseModule;
import vaughandroid.vigor.app.exercise.type.ExerciseTypeModule;
import vaughandroid.vigor.app.exercise.type.ExerciseTypePickerActivity;
import vaughandroid.vigor.app.workout.WorkoutActivity;
import vaughandroid.vigor.app.workout.WorkoutModule;

/**
 * Injector of {@link ActivityScope}d instances.
 *
 * @author Chris
 */
@Subcomponent(modules = {
        ExerciseModule.class,
        ExerciseTypeModule.class,
        WorkoutModule.class,
})
@ActivityScope
public interface ActivityComponent {

    void inject(ExerciseActivity activity);
    void inject(ExerciseTypePickerActivity activity);
    void inject(WorkoutActivity activity);
}
