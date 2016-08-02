package vaughandroid.vigor.app.workout;

import dagger.Module;
import dagger.Provides;
import vaughandroid.vigor.app.di.ActivityScope;
import vaughandroid.vigor.domain.workout.WorkoutRepository;

/**
 * @author Chris
 */
@Module public class WorkoutModule {

  @Provides @ActivityScope public WorkoutRepository provideWorkoutRepository(
      vaughandroid.vigor.data.workout.WorkoutRepository workoutRepository) {
    return workoutRepository;
  }
}
