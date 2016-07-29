package vaughandroid.vigor.data.exercise.type;

import android.support.annotation.NonNull;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Ordering;
import com.google.firebase.database.GenericTypeIndicator;

import java.text.MessageFormat;
import java.util.ArrayList;
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
    }

    @NonNull
    @Override
    public Observable<ExerciseType> addExerciseType(@NonNull ExerciseType exerciseType) {
        if (Objects.equal(exerciseType.id(), ExerciseTypeId.UNASSIGNED)) {
            exerciseType = exerciseType.withId(ExerciseTypeId.create(guidFactory.newGuid()));
        }
        final ExerciseType finalExerciseType = exerciseType;

        ExerciseTypeDto dto = mapper.fromExerciseType(finalExerciseType);
        return firebaseDatabaseWrapper.set(getPathForId(finalExerciseType.id()), dto)
                .map(ignored -> finalExerciseType);
    }

    @NonNull
    @Override
    public Observable<ExerciseType> getExerciseType(@NonNull ExerciseTypeId id) {
        return firebaseDatabaseWrapper.observe(getPathForId(id), ExerciseTypeDto.class)
                .map(mapper::fromDto);
    }

    @NonNull
    @Override
    public Observable<ImmutableList<ExerciseType>> getExerciseTypeList() {
        return firebaseDatabaseWrapper.observe(getPathForAll(), new GenericTypeIndicator<Map<String, ExerciseTypeDto>>() {})
                .map(mapper::fromDtoMap)
                .map(map -> {
                    List<ExerciseType> list = new ArrayList<>(map.size());
                    for (ExerciseType exerciseType : map.values()) {
                        list.add(exerciseType);
                    }
                    return list;
                })
                .map(this::sortList);
    }

    private ImmutableList<ExerciseType> sortList(List<ExerciseType> list) {
        return Ordering.natural().onResultOf(ExerciseType::name)
                .compound(Ordering.natural().onResultOf(ExerciseType::guid))
                .immutableSortedCopy(list);
    }

    @NonNull
    @Override
    public Observable<ImmutableMap<ExerciseTypeId, ExerciseType>> getExerciseTypeMap() {
        return firebaseDatabaseWrapper.observe(getPathForAll(), new GenericTypeIndicator<Map<String, ExerciseTypeDto>>() {})
                .map(mapper::fromDtoMap)
                .map(ImmutableMap::copyOf);
    }

    @NonNull
    private String getPathForAll() {
        return "exerciseTypes";
    }

    @NonNull
    private String getPathForId(@NonNull ExerciseTypeId id) {
        return MessageFormat.format("{}/{}", getPathForAll(), id.guid());
    }
}
