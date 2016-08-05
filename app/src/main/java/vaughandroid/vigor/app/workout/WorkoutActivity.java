package vaughandroid.vigor.app.workout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.common.collect.ImmutableList;
import javax.inject.Inject;
import vaughandroid.vigor.R;
import vaughandroid.vigor.app.VigorActivity;
import vaughandroid.vigor.app.exercise.ExerciseActivity;
import vaughandroid.vigor.app.widgets.HorizontalDividerLineItemDecoration;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.workout.WorkoutId;

public class WorkoutActivity extends VigorActivity implements WorkoutContract.View {

  private static final String EXTRA_WORKOUT_ID = "workoutId";

  private final ExerciseAdapter exerciseAdapter = new ExerciseAdapter();
  @Inject WorkoutPresenter presenter;

  @BindView(R.id.content_loading_root) View loadingRoot;
  @BindView(R.id.content_workout_root) View contentRoot;
  @BindView(R.id.content_error_root) View errorRoot;
  @BindView(R.id.content_workout_RecyclerView_exercise_list) RecyclerView exerciseListRecyclerView;

  public static Intent intentForNew(@NonNull Context context) {
    return new Intent(context, WorkoutActivity.class);
  }

  public static Intent intentForExisting(@NonNull Context context, @NonNull WorkoutId workoutId) {
    return intentForNew(context).putExtra(EXTRA_WORKOUT_ID, workoutId);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActivityComponent().inject(this);

    initViews();
    initPresenter();
  }

  private void initViews() {
    setContentView(R.layout.activity_workout);
    ButterKnife.bind(this);
    initToolbar();

    exerciseAdapter.setUserInteractionListener(exercise -> presenter.onOpenExercise(exercise));
    exerciseListRecyclerView.setAdapter(exerciseAdapter);
    exerciseListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    exerciseListRecyclerView.addItemDecoration(new HorizontalDividerLineItemDecoration(this));
    exerciseListRecyclerView.setHasFixedSize(true);
  }

  private void initPresenter() {
    presenter.setView(this);
    presenter.init(getWorkoutId());
  }

  @NonNull private WorkoutId getWorkoutId() {
    // TODO: 21/06/2016 Can simplify once we can guarantee presence of the extra
    WorkoutId result = null;
    if (getIntent().getExtras() != null) {
      result = (WorkoutId) getIntent().getExtras().getSerializable(EXTRA_WORKOUT_ID);
    }
    return result != null ? result : WorkoutId.UNASSIGNED;
  }

  private void initToolbar() {
    Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
    setSupportActionBar(toolbar);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_workout_list, menu);
    return true;
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

  @OnClick(R.id.fab) void onClickFab() {
    presenter.onAddExercise();
  }

  @Override public void setExercises(@NonNull ImmutableList<Exercise> exercises) {
    exerciseAdapter.setExercises(exercises);
  }

  @Override public void goToAddNewExercise(@NonNull WorkoutId workoutId) {
    startActivity(ExerciseActivity.intentForNew(this, workoutId));
  }

  @Override public void goToEditExistingExercise(@NonNull WorkoutId workoutId,
      @NonNull ExerciseId exerciseId) {
    startActivity(ExerciseActivity.intentForExisting(this, workoutId, exerciseId));
  }

  @Override public void showLoading() {
    loadingRoot.setVisibility(View.VISIBLE);
    contentRoot.setVisibility(View.GONE);
    errorRoot.setVisibility(View.GONE);
  }

  @Override public void showContent() {
    contentRoot.setVisibility(View.VISIBLE);
    loadingRoot.setVisibility(View.GONE);
    errorRoot.setVisibility(View.GONE);
  }

  @Override public void showError() {
    errorRoot.setVisibility(View.VISIBLE);
    loadingRoot.setVisibility(View.GONE);
    contentRoot.setVisibility(View.GONE);
  }
}
