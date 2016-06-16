package vaughandroid.vigor.app.workout;

import dagger.Module;
import dagger.Provides;
import vaughandroid.vigor.app.di.ActivityScope;
import vaughandroid.vigor.data.utils.GuidFactory;
import vaughandroid.vigor.domain.workout.WorkoutRepository;

/**
 * TODO: javadoc
 *
 * @author Chris
 */
@Module
public class WorkoutModule {

    @Provides
    @ActivityScope
    public WorkoutRepository provideWorkoutRepository(GuidFactory guidFactory) {
        return new vaughandroid.vigor.data.workout.WorkoutRepository(guidFactory);
    }
}
