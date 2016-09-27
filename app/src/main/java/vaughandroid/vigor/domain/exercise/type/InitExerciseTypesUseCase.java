package vaughandroid.vigor.domain.exercise.type;

import com.google.common.collect.ImmutableList;
import javax.inject.Inject;
import rx.Single;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;
import vaughandroid.vigor.domain.usecase.SingleUseCase;

/**
 * Use case for initialising the list of exercise types.
 *
 * @author Chris
 */
// TODO: Should use CompletableUseCase
public class InitExerciseTypesUseCase extends SingleUseCase<Boolean> {

  private final ExerciseTypeRepository exerciseTypeRepository;

  @Inject public InitExerciseTypesUseCase(SchedulingPolicy schedulingPolicy,
      ExerciseTypeRepository exerciseTypeRepository) {
    super(schedulingPolicy);
    this.exerciseTypeRepository = exerciseTypeRepository;
  }

  @Override protected Single<Boolean> createSingle() {
    return exerciseTypeRepository.isInitialised().doOnSuccess(isInitialised -> {
      if (!isInitialised) {
        exerciseTypeRepository.addExerciseTypes(
            ImmutableList.of(
                ExerciseType.create("Squat"),
                ExerciseType.create("Deadlift"),
                ExerciseType.create("Bench Press"),
                ExerciseType.create("Overhead Press"),
                ExerciseType.create("Bent-over Row")));
      }
    });
  }
}
