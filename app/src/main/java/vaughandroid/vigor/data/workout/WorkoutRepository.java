package vaughandroid.vigor.data.workout;

import android.support.annotation.NonNull;
import com.google.common.base.Objects;
import javax.inject.Inject;
import rx.Observable;
import rx.Single;
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

  @NonNull @Override public Single<Workout> addWorkout(@NonNull Workout workout) {
    if (Objects.equal(workout.id(), WorkoutId.UNASSIGNED)) {
      workout.setId(WorkoutId.create(guidFactory.newGuid()));
    }

    WorkoutDto dto = workoutMapper.fromWorkout(workout);
    return firebaseDatabaseWrapper.set(getPath(workout.id()), dto)
        .toSingleDefault(workout);
  }

  @NonNull @Override public Observable<Workout> getWorkout(@NonNull WorkoutId id) {
    return Observable.combineLatest(
        firebaseDatabaseWrapper.observe(getPath(id), WorkoutDto.class),
        exerciseTypeRepository.getExerciseTypeMap(),
        workoutMapper::fromDto);
  }

  @NonNull private String getPath(@NonNull WorkoutId id) {
    return "workouts/" + id.guid();
  }
}
