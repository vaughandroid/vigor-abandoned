package vaughandroid.vigor.domain.exercise.type;

import com.google.common.base.Preconditions;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;
import vaughandroid.vigor.domain.usecase.UseCase;

/**
 * TODO: javadoc
 *
 * @author chris.vaughan@laterooms.com
 */
public class GetExerciseTypesUseCase implements UseCase<List<ExerciseType>> {

    private final ExerciseTypeRepository exerciseTypeRepository;

    private String searchText;

    @Inject
    public GetExerciseTypesUseCase(ExerciseTypeRepository exerciseTypeRepository) {
        this.exerciseTypeRepository = exerciseTypeRepository;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    @Override
    public Observable<List<ExerciseType>> createObservable() {
        Preconditions.checkState(searchText != null, "searchText not set");
        return exerciseTypeRepository.getExerciseTypeList()
                .flatMap(new Func1<List<ExerciseType>, Observable<? extends ExerciseType>>() {
                    @Override
                    public Observable<? extends ExerciseType> call(List<ExerciseType> iterable) {
                        return Observable.from(iterable);
                    }
                })
                .filter(new Func1<ExerciseType, Boolean>() {
                    @Override
                    public Boolean call(ExerciseType exerciseType) {
                        return exerciseType.name().contains(searchText);
                    }
                })
                .toList();
    }
}
