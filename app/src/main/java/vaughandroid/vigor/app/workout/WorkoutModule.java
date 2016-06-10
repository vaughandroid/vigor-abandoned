package vaughandroid.vigor.app.workout;

import dagger.Module;
import dagger.Provides;
import vaughandroid.vigor.app.di.ActivityScope;
import vaughandroid.vigor.domain.workout.WorkoutRepository;

/**
 * TODO: javadoc
 *
 * @author chris.vaughan@laterooms.com
 */
@Module
public class WorkoutModule {

    @Provides
    @ActivityScope
    public WorkoutRepository provideWorkoutRepository() {
        return new vaughandroid.vigor.data.workout.WorkoutRepository();
    }
}
