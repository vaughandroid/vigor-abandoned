package vaughandroid.vigor.app.di;

import dagger.Subcomponent;
import vaughandroid.vigor.app.exercise.ExerciseModule;

/**
 * Injector of {@link ActivityScope}d instances.
 *
 * @author Chris
 */
@Subcomponent(modules = {
        ExerciseModule.class,
})
@ActivityScope
public interface ActivityComponent {
}
