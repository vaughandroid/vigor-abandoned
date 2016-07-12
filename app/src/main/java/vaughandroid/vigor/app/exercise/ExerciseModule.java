package vaughandroid.vigor.app.exercise;

import dagger.Module;
import dagger.Provides;
import vaughandroid.vigor.app.di.ActivityScope;
import vaughandroid.vigor.data.utils.GuidFactory;
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
    public ExerciseRepository provideExerciseRepository(GuidFactory guidFactory) {
        return new vaughandroid.vigor.data.exercise.ExerciseRepository(guidFactory);
    }
}
