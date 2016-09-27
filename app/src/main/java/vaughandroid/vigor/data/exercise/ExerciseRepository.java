package vaughandroid.vigor.data.exercise;

import android.support.annotation.NonNull;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import javax.inject.Inject;
import rx.Observable;
import rx.Single;
import vaughandroid.vigor.data.firebase.database.FirebaseDatabaseWrapper;
import vaughandroid.vigor.data.utils.GuidFactory;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.exercise.type.ExerciseTypeRepository;

/**
 * Implementation of {@link vaughandroid.vigor.domain.exercise.ExerciseRepository}
 *
 * @author Chris
 */
public class ExerciseRepository implements vaughandroid.vigor.domain.exercise.ExerciseRepository {

  private final GuidFactory guidFactory;
  private final ExerciseTypeRepository exerciseTypeRepository;
  private final ExerciseMapper exerciseMapper;
  private final FirebaseDatabaseWrapper firebaseDatabaseWrapper;

  @Inject
  public ExerciseRepository(GuidFactory guidFactory, ExerciseTypeRepository exerciseTypeRepository,
      ExerciseMapper exerciseMapper, FirebaseDatabaseWrapper firebaseDatabaseWrapper) {
    this.guidFactory = guidFactory;
    this.exerciseTypeRepository = exerciseTypeRepository;
    this.exerciseMapper = exerciseMapper;
    this.firebaseDatabaseWrapper = firebaseDatabaseWrapper;
  }

  @NonNull @Override public Single<Exercise> addExercise(@NonNull Exercise exercise) {
    if (Objects.equal(exercise.id(), ExerciseId.UNASSIGNED)) {
      exercise.setId(ExerciseId.create(guidFactory.newGuid()));
    }

    ExerciseDto dto = exerciseMapper.fromExercise(exercise);
    return firebaseDatabaseWrapper.set(getPath(exercise.id()), dto)
        .toSingleDefault(exercise);
  }

  @NonNull @Override public Observable<Exercise> getExercise(@NonNull ExerciseId id) {
    Preconditions.checkArgument(!Objects.equal(id, ExerciseId.UNASSIGNED), "not a valid exercise ID");
    return Observable.combineLatest(
        firebaseDatabaseWrapper.observe(getPath(id), ExerciseDto.class)
            // XXX: Is this ever returning null?
            .filter(dto -> {
              return dto != null;
            }),
        exerciseTypeRepository.getExerciseTypeMap(),
        exerciseMapper::fromDto);
  }

  @NonNull private String getPath(@NonNull ExerciseId id) {
    return "exercises/" + id.guid();
  }
}
