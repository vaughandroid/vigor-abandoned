package vaughandroid.vigor.app.exercise;

import com.trello.rxlifecycle.ActivityLifecycleProvider;
import java.math.BigDecimal;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import rx.Observable;
import rx.Single;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.exercise.GetExerciseUseCase;
import vaughandroid.vigor.domain.exercise.SaveExerciseUseCase;
import vaughandroid.vigor.domain.workout.WorkoutId;
import vaughandroid.vigor.testutil.StubActivityLifecycleProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit tests for {@link ExercisePresenter}
 *
 * @author Chris
 */
public class ExercisePresenterTests {

  @Rule public JUnitSoftAssertions mSoftly = new JUnitSoftAssertions();

  ActivityLifecycleProvider activityLifecycleProviderStub = new StubActivityLifecycleProvider();
  @Mock SaveExerciseUseCase saveExerciseUseCaseMock;
  @Mock GetExerciseUseCase getExerciseUseCaseMock;
  @Mock ExerciseContract.View viewMock;

  @Captor ArgumentCaptor<Exercise> exerciseArgumentCaptor;

  private final WorkoutId workoutId = WorkoutId.create("workout id");
  private final ExerciseId exerciseId = ExerciseId.create("exercise id");
  private final Exercise savedExercise = Exercise.builder()
      .id(exerciseId)
      .workoutId(workoutId)
      .build();

  ExercisePresenter presenter;

  @Before public void setup() {
    initMocks(this);
    presenter = new ExercisePresenter(activityLifecycleProviderStub, saveExerciseUseCaseMock,
        getExerciseUseCaseMock);

    when(saveExerciseUseCaseMock.setExercise(any())).thenReturn(saveExerciseUseCaseMock);
    when(saveExerciseUseCaseMock.perform()).thenReturn(Single.just(savedExercise));

    when(getExerciseUseCaseMock.setExerciseId(any())).thenReturn(getExerciseUseCaseMock);
    when(getExerciseUseCaseMock.perform()).thenReturn(Observable.just(savedExercise));
  }

  @Test public void init_for_new_exercise_shows_loading() {
    presenter.init(viewMock, workoutId, ExerciseId.UNASSIGNED);

    verify(viewMock).showLoading();
  }

  @Test public void init_for_new_exercise_saves_new_exercise_then_loads_it() {
    presenter.init(viewMock, workoutId, ExerciseId.UNASSIGNED);

    verify(saveExerciseUseCaseMock).setExercise(exerciseArgumentCaptor.capture());
    assertThat(exerciseArgumentCaptor.getValue().id()).isEqualTo(ExerciseId.UNASSIGNED);
    verify(saveExerciseUseCaseMock).perform();

    // Loading the exercise ensures that future changes are observed.
    verify(getExerciseUseCaseMock).setExerciseId(exerciseId);
    verify(getExerciseUseCaseMock).setExerciseId(exerciseId);
  }

  @Test public void init_for_existing_exercise_shows_loading() {
    presenter.init(viewMock, workoutId, exerciseId);

    verify(viewMock).showLoading();
  }

  @Test public void init_for_existing_exercise_loads_it() {
    presenter.init(viewMock, workoutId, exerciseId);

    verify(getExerciseUseCaseMock).setExerciseId(exerciseId);
    verify(getExerciseUseCaseMock).setExerciseId(exerciseId);
  }

  @Test public void onTypeClicked_opens_type_picker() {
    presenter.init(viewMock, workoutId, exerciseId);

    presenter.onTypeClicked();

    verify(viewMock).goToExerciseTypePicker(exerciseId);
  }

  @Test public void onTypeClicked_no_changes_does_not_save() {
    presenter.init(viewMock, workoutId, exerciseId);

    presenter.onTypeClicked();

    verifyZeroInteractions(saveExerciseUseCaseMock);
  }

  @Test public void onTypeClicked_saves_changes() {
    presenter.init(viewMock, workoutId, exerciseId);

    presenter.onWeightChanged(new BigDecimal("1"));
    presenter.onTypeClicked();

    verify(saveExerciseUseCaseMock).perform();
  }

  @Test public void onBack_finishes() {
    presenter.init(viewMock, workoutId, exerciseId);

    presenter.onBack();

    verify(viewMock).finish();
  }

  @Test public void onValuesConfirmed_finishes() {
    presenter.init(viewMock, workoutId, exerciseId);

    presenter.onValuesConfirmed();

    verify(viewMock).finish();
  }

  // TODO: save changes onTypeClicked
  // TODO: save changes onBack
  // TODO: save changes onValuesConfirmed
  // TODO: save changes if changed externally
  // TODO: saveExerciseUseCaseMock throws error
  // TODO: getExerciseUseCaseMock throws error

  @Test public void onError_shows_error() {
    presenter.init(viewMock, workoutId, exerciseId);

    presenter.onError(new Throwable());

    verify(viewMock).showError();
  }
}
