package vaughandroid.vigor.data.exercise.type;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import vaughandroid.vigor.data.utils.GuidFactory;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.exercise.type.ExerciseTypeId;

/**
 * TODO: javadoc
 *
 * @author Chris
 */
public class ExerciseTypeRepository implements vaughandroid.vigor.domain.exercise.type.ExerciseTypeRepository {

    private final GuidFactory guidFactory;
    private final HashMap<ExerciseTypeId, ExerciseType> lookup = new HashMap<>();

    @Inject
    public ExerciseTypeRepository(GuidFactory guidFactory) {
        this.guidFactory = guidFactory;

        // XXX need proper data
        addExerciseType(ExerciseType.create(ExerciseTypeId.UNASSIGNED, "Squats"));
        addExerciseType(ExerciseType.create(ExerciseTypeId.UNASSIGNED, "Chin-ups"));
        addExerciseType(ExerciseType.create(ExerciseTypeId.UNASSIGNED, "Bench Press"));
        addExerciseType(ExerciseType.create(ExerciseTypeId.UNASSIGNED, "Press-ups"));
        addExerciseType(ExerciseType.create(ExerciseTypeId.UNASSIGNED, "Sit-ups"));
    }

    @NotNull
    @Override
    public Observable<ExerciseType> addExerciseType(@NotNull ExerciseType exerciseType) {
        if (Objects.equal(exerciseType.id(), ExerciseTypeId.UNASSIGNED)) {
            exerciseType = exerciseType.withId(ExerciseTypeId.create(guidFactory.newGuid()));
        }
        lookup.put(exerciseType.id(), exerciseType);
        return Observable.just(exerciseType);
    }

    @NotNull
    @Override
    public Observable<ExerciseType> getExerciseType(@NotNull ExerciseTypeId id) {
        Observable<ExerciseType> result = Observable.empty();
        if (lookup.containsKey(id)) {
            result = Observable.just(lookup.get(id));
        }
        return result;
    }

    @NotNull
    @Override
    public Observable<ImmutableList<ExerciseType>> getExerciseTypeList() {
        List<ExerciseType> exerciseTypes = Lists.newArrayList(lookup.values());
        Collections.sort(exerciseTypes, new ExerciseType.NameComparator());
        return Observable.just(ImmutableList.copyOf(exerciseTypes));
    }
}