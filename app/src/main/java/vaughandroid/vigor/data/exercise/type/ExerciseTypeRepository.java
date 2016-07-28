package vaughandroid.vigor.data.exercise.type;

import android.support.annotation.NonNull;

import com.google.common.base.Objects;
import com.google.firebase.database.GenericTypeIndicator;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        final ExerciseType finalExerciseType = exerciseType;

        ExerciseTypeDto dto = mapper.fromExerciseType(finalExerciseType);
        return firebaseDatabaseWrapper.set(getPath(finalExerciseType.id()), dto)
                .map(ignored -> finalExerciseType);
    }

    @NonNull
    @Override
    public Observable<ExerciseType> getExerciseType(@NonNull ExerciseTypeId id) {
        return firebaseDatabaseWrapper.observe(getPath(id), ExerciseTypeDto.class)
                .map(mapper::fromDto);
    }

    @NonNull
    @Override
    public Observable<List<ExerciseType>> getExerciseTypeList() {
        return firebaseDatabaseWrapper.observe("exerciseTypes", new GenericTypeIndicator<List<ExerciseTypeDto>>() {})
                .map(mapper::fromDtoList);
    }

    @NonNull
    @Override
    public Observable<Map<ExerciseTypeId, ExerciseType>> getExerciseTypeMap() {
        return firebaseDatabaseWrapper.observe("exerciseTypes", new GenericTypeIndicator<List<ExerciseTypeDto>>() {})
                .map(mapper::fromDtoList)
                .map(list -> {
                    Map<ExerciseTypeId, ExerciseType> map = new HashMap<>();
                    for (ExerciseType exerciseType : list) {
                        map.put(exerciseType.id(), exerciseType);
                    }
                    return map;
                });
    }

    @NonNull
    private String getPath(@NonNull ExerciseTypeId id) {
        return MessageFormat.format("exerciseTypes/{}", id.guid());
    }
}
