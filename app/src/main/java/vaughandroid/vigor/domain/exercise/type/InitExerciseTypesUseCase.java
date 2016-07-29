package vaughandroid.vigor.domain.exercise.type;

import com.google.common.collect.ImmutableList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import rx.Observable;
import vaughandroid.vigor.domain.usecase.UseCase;

/**
 * Use case for initialising the list of exercise types.
 *
 * @author Chris
 */
public class InitExerciseTypesUseCase implements UseCase<Boolean> {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ExerciseTypeRepository exerciseTypeRepository;

    @Inject
    public InitExerciseTypesUseCase(ExerciseTypeRepository exerciseTypeRepository) {
        this.exerciseTypeRepository = exerciseTypeRepository;
    }

    @Override
    public Observable<Boolean> createObservable() {
        return exerciseTypeRepository.isInitialised()
                .doOnNext(isInitialised -> {
                    logger.debug("is initialised: {}", isInitialised);
                    if (!isInitialised) {
                        exerciseTypeRepository.addExerciseTypes(ImmutableList.of(
                                ExerciseType.create("Squat"),
                                ExerciseType.create("Deadlift"),
                                ExerciseType.create("Bench Press"),
                                ExerciseType.create("Overhead Press"),
                                ExerciseType.create("Bent-over Row")
                        ));
                    }
                });
    }
}
