package vaughandroid.vigor.domain.exercise.type;

import com.google.common.base.Preconditions;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import vaughandroid.vigor.domain.usecase.UseCase;

/**
 * TODO: javadoc
 *
 * @author chris.vaughan@laterooms.com
 */
public class GetAllExerciseTypesUseCase implements UseCase<List<ExerciseType>> {

    private final ExerciseTypeRepository exerciseTypeRepository;

    private String searchText;

    @Inject
    public GetAllExerciseTypesUseCase(ExerciseTypeRepository exerciseTypeRepository) {
        this.exerciseTypeRepository = exerciseTypeRepository;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    @Override
    public Observable<List<ExerciseType>> createObservable() {
        Preconditions.checkState(searchText != null, "searchText not set");
        return exerciseTypeRepository.getExerciseTypeList()
                .flatMap(Observable::from)
                .filter(exerciseType -> exerciseType.name().contains(searchText))
                .toList();
    }
}
