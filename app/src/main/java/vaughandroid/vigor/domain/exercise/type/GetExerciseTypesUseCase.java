package vaughandroid.vigor.domain.exercise.type;

import android.support.annotation.NonNull;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import javax.inject.Inject;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;
import vaughandroid.vigor.domain.usecase.ObservableUseCase;

/**
 * Use case for getting a list of {@link ExerciseType}s. The list may optionally be filtered.
 *
 * @author Chris
 */
public class GetExerciseTypesUseCase extends ObservableUseCase<ImmutableList<ExerciseType>> {

  private final ExerciseTypeRepository exerciseTypeRepository;
  private final Subject<String, String> searchTextSubject = BehaviorSubject.create("");

  @Inject public GetExerciseTypesUseCase(SchedulingPolicy schedulingPolicy,
      ExerciseTypeRepository exerciseTypeRepository) {
    super(schedulingPolicy);
    this.exerciseTypeRepository = exerciseTypeRepository;
  }

  // TODO: Look into whether this would be better achieved by e.g. setting an Observable for the search text.
  public void setSearchText(@NonNull String searchText) {
    searchTextSubject.onNext(searchText.toLowerCase());
  }

  @Override public Observable<ImmutableList<ExerciseType>> createObservable() {
    return Observable.combineLatest(exerciseTypeRepository.getExerciseTypeList(), searchTextSubject,
        (exerciseTypes, searchText) -> ImmutableList.copyOf(
            Iterables.filter(exerciseTypes, input -> input.nameLowercase().contains(searchText))));
  }
}
