package vaughandroid.vigor.data.exercise.type;

import android.support.annotation.NonNull;

import com.google.common.base.Objects;
import com.google.firebase.database.GenericTypeIndicator;

import java.text.MessageFormat;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import vaughandroid.vigor.data.firebase.database.FirebaseDatabaseWrapper;
import vaughandroid.vigor.data.utils.GuidFactory;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.exercise.type.ExerciseTypeId;

/**
 * Repository for dealing with {@link ExerciseType}s
 *
 * @author Chris
 */
public class ExerciseTypeRepository implements vaughandroid.vigor.domain.exercise.type.ExerciseTypeRepository {

    private final GuidFactory guidFactory;
    private final ExerciseTypeMapper mapper;
    private final FirebaseDatabaseWrapper firebaseDatabaseWrapper;

    @Inject
    public ExerciseTypeRepository(GuidFactory guidFactory, ExerciseTypeMapper mapper,
            FirebaseDatabaseWrapper firebaseDatabaseWrapper) {
        this.guidFactory = guidFactory;
        this.mapper = mapper;
        this.firebaseDatabaseWrapper = firebaseDatabaseWrapper;

        // XXX need proper data
        addExerciseType(ExerciseType.create(ExerciseTypeId.UNASSIGNED, "Squats"));
        addExerciseType(ExerciseType.create(ExerciseTypeId.UNASSIGNED, "Chin-ups"));
        addExerciseType(ExerciseType.create(ExerciseTypeId.UNASSIGNED, "Bench Press"));
        addExerciseType(ExerciseType.create(ExerciseTypeId.UNASSIGNED, "Press-ups"));
        addExerciseType(ExerciseType.create(ExerciseTypeId.UNASSIGNED, "Sit-ups"));
    }

    @NonNull
    @Override
    public Observable<ExerciseType> addExerciseType(@NonNull ExerciseType exerciseType) {
        if (Objects.equal(exerciseType.id(), ExerciseTypeId.UNASSIGNED)) {
            exerciseType = exerciseType.withId(ExerciseTypeId.create(guidFactory.newGuid()));
        }
        return Observable.just(exerciseType);
    }

    @NonNull
    @Override
    public Observable<ExerciseType> getExerciseType(@NonNull ExerciseTypeId id) {
        String path = MessageFormat.format("exerciseTypes/{}", id.guid());
        return firebaseDatabaseWrapper.observe(path, ExerciseTypeDto.class)
                .map(mapper::fromDto);
    }

    @NonNull
    @Override
    public Observable<List<ExerciseType>> getExerciseTypeList() {
        return firebaseDatabaseWrapper.observe("exerciseTypes", new GenericTypeIndicator<List<ExerciseTypeDto>>() {})
                .map(mapper::fromDtoList);
    }
}
