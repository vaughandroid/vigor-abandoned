package vaughandroid.vigor.app.exercise;

import dagger.Module;
import dagger.Provides;
import vaughandroid.vigor.app.di.ActivityScope;
import vaughandroid.vigor.data.exercise.ExerciseMapper;
import vaughandroid.vigor.data.firebase.database.FirebaseDatabaseWrapper;
import vaughandroid.vigor.data.utils.GuidFactory;
import vaughandroid.vigor.domain.exercise.ExerciseRepository;
import vaughandroid.vigor.domain.exercise.type.ExerciseTypeRepository;

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
    public ExerciseRepository provideExerciseRepository(GuidFactory guidFactory,
            ExerciseTypeRepository exerciseTypeRepository, ExerciseMapper exerciseMapper,
            FirebaseDatabaseWrapper firebaseDatabaseWrapper) {
        return new vaughandroid.vigor.data.exercise.ExerciseRepository(guidFactory, exerciseTypeRepository,
                exerciseMapper, firebaseDatabaseWrapper);
    }
}
