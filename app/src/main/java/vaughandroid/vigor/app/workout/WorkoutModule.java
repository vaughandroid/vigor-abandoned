package vaughandroid.vigor.app.workout;

import dagger.Module;
import dagger.Provides;
import vaughandroid.vigor.app.di.ActivityScope;
import vaughandroid.vigor.data.firebase.database.FirebaseDatabaseWrapper;
import vaughandroid.vigor.data.utils.GuidFactory;
import vaughandroid.vigor.data.workout.WorkoutMapper;
import vaughandroid.vigor.domain.exercise.type.ExerciseTypeRepository;
import vaughandroid.vigor.domain.workout.WorkoutRepository;

/**
 * @author Chris
 */
@Module
public class WorkoutModule {

    @Provides
    @ActivityScope
    public WorkoutRepository provideWorkoutRepository(GuidFactory guidFactory,
            ExerciseTypeRepository exerciseTypeRepository, WorkoutMapper workoutMapper,
            FirebaseDatabaseWrapper firebaseDatabaseWrapper) {
        return new vaughandroid.vigor.data.workout.WorkoutRepository(guidFactory, exerciseTypeRepository, workoutMapper,
                firebaseDatabaseWrapper);
    }
}
