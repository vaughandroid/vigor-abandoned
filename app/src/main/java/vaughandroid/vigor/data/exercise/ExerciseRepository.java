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

    @NonNull
    @Override
    public Observable<Exercise> addExercise(@NonNull Exercise exercise) {
        if (Objects.equal(exercise.id(), ExerciseId.UNASSIGNED)) {
            exercise = exercise.withId(ExerciseId.create(guidFactory.newGuid()));
        }
        // XXX
        return Observable.just(exercise);
    }

    @NonNull
    @Override
    public Observable<Exercise> getExercise(@NonNull ExerciseId id) {
        String path = MessageFormat.format("exercises/{}", id.guid());
        return Observable.combineLatest(
                firebaseDatabaseWrapper.observe(path, ExerciseDto.class),
                exerciseTypeRepository.getExerciseTypeMap(),
                exerciseMapper::fromDto);
    }
}
