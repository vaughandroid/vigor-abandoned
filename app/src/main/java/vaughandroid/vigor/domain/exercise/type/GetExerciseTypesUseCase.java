package vaughandroid.vigor.domain.exercise.type;

import android.support.annotation.NonNull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;
import vaughandroid.vigor.domain.usecase.UseCase;

/**
 * TODO: javadoc
 *
 * @author chris.vaughan@laterooms.com
 */
public class GetExerciseTypesUseCase implements UseCase<ImmutableList<ExerciseType>> {

    private final ExerciseTypeRepository exerciseTypeRepository;
    private final Subject<String, String> searchTextSubject = BehaviorSubject.create("");

    @Inject
    public GetExerciseTypesUseCase(ExerciseTypeRepository exerciseTypeRepository) {
        this.exerciseTypeRepository = exerciseTypeRepository;
    }

    // TODO: Look into whether this would be better achieved by e.g. setting an Observable for the search text.
    public void setSearchText(@NonNull String searchText) {
        searchTextSubject.onNext(searchText.toLowerCase());
    }

    @Override
    public Observable<ImmutableList<ExerciseType>> createObservable() {
        return Observable.combineLatest(
                exerciseTypeRepository.getExerciseTypeList(),
                searchTextSubject,
                (exerciseTypes, searchText) ->
                        ImmutableList.copyOf(Iterables.filter(exerciseTypes, input -> input.nameLowercase().contains(searchText))));
    }
}
