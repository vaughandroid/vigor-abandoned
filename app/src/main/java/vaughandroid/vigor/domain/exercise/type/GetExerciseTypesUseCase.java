package vaughandroid.vigor.domain.exercise.type;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import vaughandroid.vigor.domain.usecase.UseCase;
import vaughandroid.vigor.utils.Lists;

/**
 * TODO: javadoc
 *
 * @author Chris
 */
public class GetExerciseTypesUseCase implements UseCase<List<ExerciseType>> {

    private final ExerciseTypeRepository exerciseTypeRepository;

    private String searchText;

    @Inject
    public GetExerciseTypesUseCase(ExerciseTypeRepository exerciseTypeRepository) {
        this.exerciseTypeRepository = exerciseTypeRepository;
    }

    public void setSearchText(@NonNull String searchText) {
        // XXX must re-subscribe to the use case each time the search text is changed. Could possibly change this by making the search text observable
        this.searchText = searchText;
    }

    @Override
    public Observable<List<ExerciseType>> createObservable() {
        return exerciseTypeRepository.getExerciseTypeList()
                .map(list -> Lists.filterList(list, input -> input.name().contains(searchText)));
    }
}
