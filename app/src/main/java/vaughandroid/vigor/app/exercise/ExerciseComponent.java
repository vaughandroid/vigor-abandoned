package vaughandroid.vigor.app.exercise;

import dagger.Subcomponent;
import vaughandroid.vigor.app.di.ActivityScope;

/**
 * TODO: javadoc
 *
 * @author chris.vaughan@laterooms.com
 */
@Subcomponent(modules = {
        ExerciseModule.class,
})
@ActivityScope
public interface ExerciseComponent {
    void inject(ExerciseActivity activity);
}
