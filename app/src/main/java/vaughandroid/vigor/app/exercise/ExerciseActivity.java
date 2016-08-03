/*
 * Copyright (c) 2016 Christopher John Vaughan. All rights reserved.
 */
package vaughandroid.vigor.app.exercise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.common.base.Preconditions;
import java.math.BigDecimal;
import javax.inject.Inject;
import vaughandroid.vigor.R;
import vaughandroid.vigor.app.VigorActivity;
import vaughandroid.vigor.app.dialogs.ErrorDialogFragment;
import vaughandroid.vigor.app.errors.UnexpectedActivityResultException;
import vaughandroid.vigor.app.exercise.type.ExerciseTypePickerActivity;
import vaughandroid.vigor.app.widgets.NumberInputView;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * @author Chris
 */
public class ExerciseActivity extends VigorActivity implements ExerciseContract.View {

  private static final String EXTRA_WORKOUT_ID = "workoutId";
  private static final String EXTRA_EXERCISE_ID = "savedExerciseId";

  private static final String TAG_ERROR_DIALOG = "ErrorDialog";

  private static final String RESULT_EXERCISE = "exercise";

  private static final int REQUEST_CODE_PICK_TYPE = 1;
  @Inject ExerciseContract.Presenter presenter;
  @BindView(R.id.content_exercise_TextView_type) TextView typeTextView;
  @BindView(R.id.content_exercise_NumberInputView_weight) NumberInputView weightNumberInputView;
  @BindView(R.id.content_exercise_NumberInputView_reps) NumberInputView repsNumberInputView;

  public static Intent intentForNew(@NonNull Context context, @NonNull WorkoutId workoutId) {
    return intentForExisting(context, workoutId, ExerciseId.UNASSIGNED);
  }

  public static Intent intentForExisting(@NonNull Context context, @NonNull WorkoutId workoutId,
      @NonNull ExerciseId exerciseId) {
    return new Intent(context, ExerciseActivity.class).putExtra(EXTRA_WORKOUT_ID, workoutId)
        .putExtra(EXTRA_EXERCISE_ID, exerciseId);
  }

  @NonNull public static Exercise getExerciseFromResult(@NonNull Intent data) {
    if (!data.hasExtra(RESULT_EXERCISE)) {
      throw new IllegalArgumentException("Invalid data");
    }
    return (Exercise) data.getSerializableExtra(RESULT_EXERCISE);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActivityComponent().inject(this);

    initViews();
    initPresenter();
  }

  private void initViews() {
    setContentView(R.layout.activity_exercise);
    ButterKnife.bind(this);
    initToolbar();

    weightNumberInputView.setUnitsShown(true);
  }

  private void initToolbar() {
    Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
    setSupportActionBar(toolbar);
    assert getSupportActionBar() != null;
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
  }

  private void initPresenter() {
    presenter.setView(this);
    presenter.init(getWorkoutId(), getExerciseId());
  }

  @NonNull private WorkoutId getWorkoutId() {
    WorkoutId workoutId = (WorkoutId) getIntent().getExtras().getSerializable(EXTRA_WORKOUT_ID);
    Preconditions.checkNotNull(workoutId, "Missing extra: %s", EXTRA_WORKOUT_ID);
    return workoutId;
  }

  @NonNull private ExerciseId getExerciseId() {
    ExerciseId exerciseId = (ExerciseId) getIntent().getExtras().getSerializable(EXTRA_EXERCISE_ID);
    Preconditions.checkNotNull(exerciseId, "Missing extra: %s", EXTRA_EXERCISE_ID);
    return exerciseId;
  }

  @Override public void showError() {
    ErrorDialogFragment.create().show(getSupportFragmentManager(), TAG_ERROR_DIALOG);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onSupportNavigateUp() {
    finish();
    return true;
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    switch (requestCode) {
      case REQUEST_CODE_PICK_TYPE:
        if (resultCode == RESULT_OK) {
          presenter.onTypePicked(ExerciseTypePickerActivity.getTypeFromResult(data));
        }
        break;
      default:
        throw new UnexpectedActivityResultException(requestCode, resultCode, data);
    }
  }

  @OnClick(R.id.content_exercise_Button_confirm) void onClickConfirmButton() {
    presenter.onValuesConfirmed(weightNumberInputView.getValue(),
        repsNumberInputView.getIntValue());
  }

  @Override public void setType(@NonNull ExerciseType type) {
    typeTextView.setText(type.name());
  }

  @OnClick(R.id.content_exercise_TextView_type) void onTypeClicked() {
    presenter.onTypeClicked();
  }

  @Override public void setWeight(@Nullable BigDecimal weight, @NonNull String units) {
    weightNumberInputView.setValue(weight);
    weightNumberInputView.setUnits(units);
  }

  @Override public void setReps(@Nullable Integer reps) {
    repsNumberInputView.setValue(reps);
  }

  @Override public void goToExerciseTypePicker(@NonNull ExerciseType type) {
    startActivityForResult(ExerciseTypePickerActivity.intent(this, type), REQUEST_CODE_PICK_TYPE);
  }

  @Override public void onSaved(@NonNull Exercise exercise) {
    setResult(RESULT_OK, new Intent().putExtra(RESULT_EXERCISE, exercise));
    finish();
  }
}
