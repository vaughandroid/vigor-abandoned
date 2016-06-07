package vaughandroid.vigor.app.exercise;

import dagger.Module;
import dagger.Provides;
import vaughandroid.vigor.app.di.ActivityScope;

/**
 * Provides exercise-related classes for injection.
 *
 * @author Chris
 */
@Module
public class ExerciseModule {

    @Provides
    @ActivityScope
    public ExerciseContract.Presenter provideExercisePresenter(ExercisePresenter presenter) {
        return presenter;
    }
}
