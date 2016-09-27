/*
 * Copyright (c) 2016 Christopher John Vaughan. All rights reserved.
 */
package vaughandroid.vigor.app.exercise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.common.base.Preconditions;
import com.trello.rxlifecycle.ActivityEvent;
import java.math.BigDecimal;
import javax.inject.Inject;
import vaughandroid.vigor.R;
import vaughandroid.vigor.app.VigorActivity;
import vaughandroid.vigor.app.exercise.type.ExerciseTypePickerActivity;
import vaughandroid.vigor.app.widgets.NumberInputView;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * @author Chris
 */
public class ExerciseActivity extends VigorActivity implements ExerciseContract.View {

  @VisibleForTesting public static final String EXTRA_WORKOUT_ID = "workoutId";
  @VisibleForTesting public static final String EXTRA_EXERCISE_ID = "savedExerciseId";

  @Inject ExerciseContract.Presenter presenter;
  @BindView(R.id.content_exercise_root) View contentRootView;
  @BindView(R.id.content_loading_root) View loadingRootView;
  @BindView(R.id.content_error_root) View errorRootView;
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

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActivityComponent().inject(this);

    setTitle(R.string.title_activity_exercise);
    initViews();
    initPresenter();
  }

  private void initViews() {
    setContentView(R.layout.activity_exercise);
    ButterKnife.bind(this);
    initToolbar();

    weightNumberInputView.setUnitsShown(true);
    weightNumberInputView.valueObservable()
        .compose(bindUntilEvent(ActivityEvent.DESTROY))
        .subscribe(presenter::onWeightChanged, presenter::onError);

    repsNumberInputView.valueObservable()
        .compose(bindUntilEvent(ActivityEvent.DESTROY))
        .map(BigDecimal::intValue)
        .subscribe(presenter::onRepsChanged, presenter::onError);
  }

  private void initToolbar() {
    Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
    setSupportActionBar(toolbar);
    assert getSupportActionBar() != null;
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
  }

  private void initPresenter() {
    presenter.init(this, getWorkoutId(), getExerciseId());
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

  @Override public void showContent() {
    contentRootView.setVisibility(View.VISIBLE);
    loadingRootView.setVisibility(View.GONE);
    errorRootView.setVisibility(View.GONE);
  }

  @Override public void showLoading() {
    loadingRootView.setVisibility(View.VISIBLE);
    contentRootView.setVisibility(View.GONE);
    errorRootView.setVisibility(View.GONE);
  }

  @Override public void showError() {
    errorRootView.setVisibility(View.VISIBLE);
    contentRootView.setVisibility(View.GONE);
    loadingRootView.setVisibility(View.GONE);
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

  @Override public void onBackPressed() {
    presenter.onBack();
  }

  @Override public boolean onSupportNavigateUp() {
    presenter.onBack();
    return false;
  }

  @OnClick(R.id.content_exercise_Button_confirm) void onClickConfirmButton() {
    presenter.onValuesConfirmed();
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

  @Override public void goToExerciseTypePicker(@NonNull ExerciseId exerciseId) {
    startActivity(ExerciseTypePickerActivity.intent(this, exerciseId));
  }
}
