package vaughandroid.vigor.domain.exercise.type;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;
import vaughandroid.vigor.domain.usecase.UseCase;
import vaughandroid.vigor.utils.Lists;

/**
 * TODO: javadoc
 *
 * @author chris.vaughan@laterooms.com
 */
public class GetExerciseTypesUseCase implements UseCase<List<ExerciseType>> {

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
    public Observable<List<ExerciseType>> createObservable() {
        return Observable.combineLatest(
                exerciseTypeRepository.getExerciseTypeList(),
                searchTextSubject,
                (exerciseTypes, searchText) ->
                        Lists.filterList(exerciseTypes, input -> input.nameLowercase().contains(searchText)));
    }
}
