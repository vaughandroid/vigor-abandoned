package vaughandroid.vigor.data.exercise;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import rx.Observable;
import rx.observers.TestSubscriber;
import vaughandroid.vigor.data.utils.GuidFactory;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.exercise.type.ExerciseTypeId;
import vaughandroid.vigor.domain.exercise.type.ExerciseTypeRepository;
import vaughandroid.vigor.domain.workout.WorkoutId;
import vaughandroid.vigor.testutils.StubFirebaseDatabaseWrapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit tests for {@link ExerciseRepository}
 *
 * @author Chris
 */
// TODO: Test errors, but need to add support to StubFirebaseDatabaseWrapper
// TODO: Get non-existent exercise should throw an error
public class ExerciseRepositoryTests {

  @Mock ExerciseTypeRepository exerciseTypeRepositoryMock;
  @Mock GuidFactory guidFactoryMock;

  final TestSubscriber<Exercise> testSubscriber = new TestSubscriber<>();
  final ExerciseMapper exerciseMapper = new ExerciseMapper();
  final StubFirebaseDatabaseWrapper stubFirebaseDatabaseWrapper = new StubFirebaseDatabaseWrapper();

  final WorkoutId workoutId = WorkoutId.create("workout id");
  final ExerciseType exerciseType =
      ExerciseType.create(ExerciseTypeId.create("exercise type id"), "exercise type");
  final ExerciseId exerciseId = ExerciseId.create("exercise id");
  final Exercise existingExercise = Exercise.builder()
      .id(exerciseId)
      .type(exerciseType)
      .workoutId(workoutId)
      .weight("100")
      .reps(10)
      .build();
  final Exercise newExercise = Exercise.builder().workoutId(workoutId).build();

  ExerciseRepository repository;

  @Before public void setup() {
    initMocks(this);
    repository = new ExerciseRepository(guidFactoryMock, exerciseTypeRepositoryMock, exerciseMapper,
        stubFirebaseDatabaseWrapper);
  }

  @Test public void addExercise_existing_exercise() {
    repository.addExercise(existingExercise).subscribe(testSubscriber);

    // Check it was stored in Firebase.
    assertThat(stubFirebaseDatabaseWrapper.data).containsKey("exercises/exercise id");

    // Check the emitted value.
    testSubscriber.assertValue(existingExercise);
    testSubscriber.assertCompleted();
  }

  @Test public void addExercise_new_exercise() {
    when(guidFactoryMock.newGuid()).thenReturn("new guid");

    repository.addExercise(newExercise).subscribe(testSubscriber);

    // Check it was stored in Firebase.
    assertThat(stubFirebaseDatabaseWrapper.data).containsKey("exercises/new guid");

    // Check the emitted value.
    testSubscriber.assertValue(newExercise);
    testSubscriber.assertCompleted();
  }

  @Test public void getExercise_existing_exercise() {
    initExerciseTypeMap();
    stubFirebaseDatabaseWrapper.set("exercises/exercise id",
        exerciseMapper.fromExercise(existingExercise));

    repository.getExercise(exerciseId).subscribe(testSubscriber);

    testSubscriber.assertValue(existingExercise);
    testSubscriber.assertCompleted();
  }

  /**
   * Whatever we store, we should be able to retrieve. This tests the mapping between domain object
   * and DTO.
   */
  @Test public void add_then_get() {
    initExerciseTypeMap();

    repository.addExercise(existingExercise).subscribe();
    repository.getExercise(exerciseId).subscribe(testSubscriber);

    testSubscriber.assertValue(existingExercise);
    testSubscriber.assertCompleted();
  }

  private void initExerciseTypeMap() {
    ImmutableMap<ExerciseTypeId, ExerciseType> map =
        ImmutableMap.<ExerciseTypeId, ExerciseType>builder()
            .put(exerciseType.id(), exerciseType)
            .build();
    when(exerciseTypeRepositoryMock.getExerciseTypeMap()).thenReturn(
        Observable.just(map));
  }
}
