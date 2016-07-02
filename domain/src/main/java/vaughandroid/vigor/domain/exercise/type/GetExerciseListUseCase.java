package vaughandroid.vigor.domain.exercise.type;

import com.google.common.collect.ImmutableList;

import javax.inject.Inject;

import rx.Observable;
import vaughandroid.vigor.domain.usecase.UseCase;

/**
 * TODO: javadoc
 *
 * @author chris.vaughan@laterooms.com
 */
public class GetExerciseListUseCase implements UseCase<ImmutableList<ExerciseType>> {

    private final ExerciseTypeRepository exerciseTypeRepository;

    @Inject
    public GetExerciseListUseCase(ExerciseTypeRepository exerciseTypeRepository) {
        this.exerciseTypeRepository = exerciseTypeRepository;
    }

    @Override
    public Observable<ImmutableList<ExerciseType>> createObservable() {
        return exerciseTypeRepository.getExerciseTypeList();
    }
}
