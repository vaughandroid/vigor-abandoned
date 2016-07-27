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
import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.exercise.type.ExerciseTypeId;
import vaughandroid.vigor.domain.exercise.type.ExerciseTypeRepository;

/**
 * Implementation of {@link vaughandroid.vigor.domain.exercise.ExerciseRepository}
 *
 * @author Chris
 */
public class ExerciseRepository implements vaughandroid.vigor.domain.exercise.ExerciseRepository {

    private final GuidFactory guidFactory;
    private final ExerciseTypeRepository exerciseTypeRepository;
    private final FirebaseDatabaseWrapper firebaseDatabaseWrapper;

    @Inject
    public ExerciseRepository(GuidFactory guidFactory, ExerciseTypeRepository exerciseTypeRepository,
            FirebaseDatabaseWrapper firebaseDatabaseWrapper) {
        this.guidFactory = guidFactory;
        this.exerciseTypeRepository = exerciseTypeRepository;
        this.firebaseDatabaseWrapper = firebaseDatabaseWrapper;
    }

    @NonNull
    @Override
    public Observable<Exercise> addExercise(@NonNull Exercise exercise) {
        if (Objects.equal(exercise.id(), ExerciseId.UNASSIGNED)) {
            exercise = exercise.withId(ExerciseId.create(guidFactory.newGuid()));
        }
        return Observable.just(exercise);
    }

    @NonNull
    @Override
    public Observable<Exercise> getExercise(@NonNull ExerciseId id) {
        String path = MessageFormat.format("exercises/{}", id.guid());
        return firebaseDatabaseWrapper.observe(path, ExerciseDto.class)
                .flatMap(
                        // Fetch type
                        dto -> exerciseTypeRepository.getExerciseType(ExerciseTypeId.create(dto.type.guid)),
                        // Combine DTO and type
                        (ExerciseDto dto, ExerciseType type) -> new ExerciseMapper().fromDto(dto, type));

    }
}
