package vaughandroid.vigor.data.exercise;

import android.support.annotation.NonNull;
import com.google.common.base.Objects;
import java.text.MessageFormat;
import javax.inject.Inject;
import rx.Observable;
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

  @NonNull @Override public Observable<Exercise> addExercise(@NonNull Exercise exercise) {
    if (Objects.equal(exercise.id(), ExerciseId.UNASSIGNED)) {
      exercise.setId(ExerciseId.create(guidFactory.newGuid()));
    }
    final Exercise finalExercise = exercise;

    ExerciseDto dto = exerciseMapper.fromExercise(finalExercise);
    return firebaseDatabaseWrapper.set(getPath(finalExercise.id()), dto)
        .map(ignored -> finalExercise);
  }

  @NonNull @Override public Observable<Exercise> getExercise(@NonNull ExerciseId id) {
    // TODO: 25/09/2016 Probably want a different operator here which will wait for both to return a value - maybe Join?
    return Observable.combineLatest(
        firebaseDatabaseWrapper.observe(getPath(id), ExerciseDto.class),
        exerciseTypeRepository.getExerciseTypeMap(),
        (dto, map) -> {
          if (dto != null && map != null) {
            return exerciseMapper.fromDto(dto, map);
          }
          return null;
        })
        .filter(exercise -> exercise != null);
  }

  @NonNull private String getPath(@NonNull ExerciseId id) {
    return MessageFormat.format("exercises/{}", id.guid());
  }
}
