package vaughandroid.vigor.data.workout;

import android.support.annotation.NonNull;

import com.google.common.base.Objects;

import java.text.MessageFormat;

import javax.inject.Inject;

import rx.Observable;
import vaughandroid.vigor.data.firebase.database.FirebaseDatabaseWrapper;
import vaughandroid.vigor.data.utils.GuidFactory;
import vaughandroid.vigor.domain.exercise.type.ExerciseTypeRepository;
import vaughandroid.vigor.domain.workout.Workout;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * Repository for dealing with {@link Workout}s
 *
 * @author Chris
 */
public class WorkoutRepository implements vaughandroid.vigor.domain.workout.WorkoutRepository {

    private final GuidFactory guidFactory;
    private final ExerciseTypeRepository exerciseTypeRepository;
    private final WorkoutMapper workoutMapper;
    private final FirebaseDatabaseWrapper firebaseDatabaseWrapper;

    @Inject
    public WorkoutRepository(GuidFactory guidFactory, ExerciseTypeRepository exerciseTypeRepository,
            WorkoutMapper workoutMapper, FirebaseDatabaseWrapper firebaseDatabaseWrapper) {
        this.guidFactory = guidFactory;
        this.exerciseTypeRepository = exerciseTypeRepository;
        this.workoutMapper = workoutMapper;
        this.firebaseDatabaseWrapper = firebaseDatabaseWrapper;
    }

    @NonNull
    @Override
    public Observable<Workout> addWorkout(@NonNull Workout workout) {
        if (Objects.equal(workout.id(), WorkoutId.UNASSIGNED)) {
            workout = workout.withId(WorkoutId.create(guidFactory.newGuid()));
        }
        // XXX
        return Observable.just(workout);
    }

    @NonNull
    @Override
    public Observable<Workout> getWorkout(@NonNull WorkoutId id) {
        String path = MessageFormat.format("workouts/{}", id.guid());
        return Observable.combineLatest(
                firebaseDatabaseWrapper.observe(path, WorkoutDto.class),
                exerciseTypeRepository.getExerciseTypeMap(),
                workoutMapper::fromDto);
    }
}
