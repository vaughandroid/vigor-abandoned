package vaughandroid.vigor.app.exercise;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import vaughandroid.vigor.app.di.ActivityScope;
import vaughandroid.vigor.domain.exercise.ExerciseRepository;

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

    @Provides
    @ActivityScope
    @Binds
    public ExerciseRepository provideExerciseRepository(vaughandroid.vigor.data.exercise.ExerciseRepository exerciseRepository) {
        return exerciseRepository;
    }
}
