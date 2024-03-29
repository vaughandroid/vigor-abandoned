package vaughandroid.vigor.data.exercise.type;

import android.support.annotation.NonNull;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Ordering;
import com.google.firebase.database.GenericTypeIndicator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import rx.Observable;
import rx.Single;
import vaughandroid.vigor.data.firebase.database.FirebaseDatabaseWrapper;
import vaughandroid.vigor.data.utils.GuidFactory;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.exercise.type.ExerciseTypeId;

/**
 * Repository for dealing with {@link ExerciseType}s
 *
 * @author Chris
 */
public class ExerciseTypeRepository
    implements vaughandroid.vigor.domain.exercise.type.ExerciseTypeRepository {

  private final GuidFactory guidFactory;
  private final ExerciseTypeMapper mapper;
  private final FirebaseDatabaseWrapper firebaseDatabaseWrapper;

  @Inject public ExerciseTypeRepository(GuidFactory guidFactory, ExerciseTypeMapper mapper,
      FirebaseDatabaseWrapper firebaseDatabaseWrapper) {
    this.guidFactory = guidFactory;
    this.mapper = mapper;
    this.firebaseDatabaseWrapper = firebaseDatabaseWrapper;
  }

  @NonNull @Override public Single<Boolean> isInitialised() {
    return firebaseDatabaseWrapper.dataExists(getPathForAll());
  }

  @NonNull @Override
  public Single<ExerciseType> addExerciseType(@NonNull ExerciseType exerciseType) {
    if (Objects.equal(exerciseType.id(), ExerciseTypeId.UNASSIGNED)) {
      exerciseType = exerciseType.withId(ExerciseTypeId.create(guidFactory.newGuid()));
    }

    ExerciseTypeDto dto = mapper.fromExerciseType(exerciseType);
    return firebaseDatabaseWrapper.set(getPathForId(exerciseType.id()), dto)
        .toSingleDefault(exerciseType);
  }

  @NonNull @Override public Single<ImmutableList<ExerciseType>> addExerciseTypes(
      @NonNull Iterable<ExerciseType> exerciseTypes) {
    List<Single<ExerciseType>> list = new ArrayList<>();
    for (ExerciseType exerciseType : exerciseTypes) {
      list.add(addExerciseType(exerciseType));
    }
    return Single.zip(list, addedTypes -> {
      // We get an array of Objects, so have to cast each one individually.
      ImmutableList.Builder<ExerciseType> builder = ImmutableList.builder();
      for (Object o : addedTypes) {
        builder.add((ExerciseType) o);
      }
      return builder.build();
    });
  }

  @NonNull @Override public Observable<ExerciseType> getExerciseType(@NonNull ExerciseTypeId id) {
    return firebaseDatabaseWrapper.observe(getPathForId(id), ExerciseTypeDto.class)
        .map(mapper::fromDto);
  }

  @NonNull @Override public Observable<ImmutableList<ExerciseType>> getExerciseTypeList() {
    return getExerciseTypeMap().map(map -> {
      List<ExerciseType> list = new ArrayList<>(map.size());
      for (ExerciseType exerciseType : map.values()) {
        list.add(exerciseType);
      }
      return list;
    }).map(this::sortList);
  }

  private ImmutableList<ExerciseType> sortList(List<ExerciseType> list) {
    return Ordering.natural()
        .onResultOf(ExerciseType::name)
        .compound(Ordering.natural().onResultOf(ExerciseType::guid))
        .immutableSortedCopy(list);
  }

  @NonNull @Override
  public Observable<ImmutableMap<ExerciseTypeId, ExerciseType>> getExerciseTypeMap() {
    //noinspection Convert2Diamond
    return firebaseDatabaseWrapper.observe(getPathForAll(),
        new GenericTypeIndicator<Map<String, ExerciseTypeDto>>() {
        })
        .map(map -> map != null ? map : new HashMap<String, ExerciseTypeDto>())
        .map(mapper::fromDtoMap)
        .map(ImmutableMap::copyOf);
  }

  @NonNull private String getPathForAll() {
    return "exerciseTypes";
  }

  @NonNull private String getPathForId(@NonNull ExerciseTypeId id) {
    return getPathForAll() + "/" + id.guid();
  }
}
