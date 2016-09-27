package vaughandroid.vigor.domain.exercise;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;
import vaughandroid.vigor.domain.workout.WorkoutId;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit tests for {@link GetExerciseUseCase}
 *
 * @author Chris
 */
public class GetExerciseUseCaseTests {

  @Mock ExerciseRepository exerciseRepositoryMock;

  final SchedulingPolicy schedulingPolicy =
      new SchedulingPolicy(Schedulers.immediate(), Schedulers.immediate());

  final TestSubscriber<Exercise> testSubscriber = new TestSubscriber<>();

  GetExerciseUseCase useCase;

  @Before public void setup() {
    initMocks(this);
    useCase = new GetExerciseUseCase(schedulingPolicy, exerciseRepositoryMock);
  }

  @Test(expected = IllegalStateException.class)
  public void getObservable_without_setExerciseId_throws_IllegalStateException() {
    useCase.getObservable();
  }

  @Test public void getObservable_successful() {
    ExerciseId exerciseId = ExerciseId.create("guid");
    Exercise exercise = Exercise.builder()
        .id(exerciseId)
        .workoutId(WorkoutId.create("workout id"))
        .build();
    when(exerciseRepositoryMock.getExercise(any())).thenReturn(Observable.just(exercise));

    useCase.setExerciseId(exerciseId);
    useCase.getObservable().subscribe(testSubscriber);

    verify(exerciseRepositoryMock).getExercise(exerciseId);
    verifyNoMoreInteractions(exerciseRepositoryMock);

    testSubscriber.assertValues(exercise);
    testSubscriber.assertCompleted();
  }

  @Test public void getObservable_error() {
    ExerciseId exerciseId = ExerciseId.create("guid");
    Throwable error = new Throwable();
    when(exerciseRepositoryMock.getExercise(any())).thenReturn(Observable.error(error));

    useCase.setExerciseId(exerciseId);
    useCase.getObservable().subscribe(testSubscriber);

    verify(exerciseRepositoryMock).getExercise(exerciseId);
    verifyNoMoreInteractions(exerciseRepositoryMock);

    testSubscriber.assertError(error);
  }
}
