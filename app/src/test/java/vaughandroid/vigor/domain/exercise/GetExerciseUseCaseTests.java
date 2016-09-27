package vaughandroid.vigor.domain.exercise;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import rx.Observable;
import rx.schedulers.Schedulers;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;

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

  SchedulingPolicy schedulingPolicy = new SchedulingPolicy(Schedulers.immediate(), Schedulers.immediate());
  GetExerciseUseCase useCase;

  @Before public void setup() {
    initMocks(this);
    useCase = new GetExerciseUseCase(schedulingPolicy, exerciseRepositoryMock);

    when(exerciseRepositoryMock.getExercise(any())).thenReturn(Observable.empty());
  }

  @Test(expected = IllegalStateException.class)
  public void getObservable_without_calling_setExerciseId_throws_IllegalStateException() {
    useCase.getObservable();
  }

  @Test
  public void getObservable_calls_getExercise() {
    ExerciseId exerciseId = ExerciseId.create("guid");
    useCase.setExerciseId(exerciseId);
    useCase.getObservable();

    verify(exerciseRepositoryMock).getExercise(exerciseId);
    verifyNoMoreInteractions(exerciseRepositoryMock);
  }
}
