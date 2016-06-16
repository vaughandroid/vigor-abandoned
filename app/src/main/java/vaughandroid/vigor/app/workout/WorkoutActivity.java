package vaughandroid.vigor.app.workout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vaughandroid.vigor.R;
import vaughandroid.vigor.app.VigorActivity;
import vaughandroid.vigor.app.exercise.ExerciseActivity;
import vaughandroid.vigor.domain.exercise.Exercise;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.workout.WorkoutId;

public class WorkoutActivity extends VigorActivity implements WorkoutContract.View {

    private static final String EXTRA_WORKOUT_ID = "workoutId";

    public static Intent intentForNew(@NonNull Context context) {
        return new Intent(context, WorkoutActivity.class);
    }

    public static Intent intentForExisting(@NonNull Context context, @NonNull WorkoutId workoutId) {
        return intentForNew(context).putExtra(EXTRA_WORKOUT_ID, workoutId);
    }

    private WorkoutPresenter presenter;

    @BindView(R.id.content_workout_foo) TextView foo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);

        setContentView(R.layout.activity_workout);
        ButterKnife.bind(this);
        initToolbar();
    }

    @Inject
    void inject(WorkoutPresenter presenter) {
        this.presenter = presenter;
        presenter.setView(this);
        presenter.setWorkoutId(getWorkoutId());
    }

    @Nullable
    private WorkoutId getWorkoutId() {
        return (WorkoutId) getIntent().getExtras().getSerializable(EXTRA_WORKOUT_ID);
    }

    private void initToolbar() {
        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_workout_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    @OnClick(R.id.fab)
    void onClickFab() {
        presenter.onAddExercise();
    }

    @Override
    public void setExercises(@NonNull List<Exercise> exercises) {
        foo.setText(exercises.size() + " exercises");
    }

    @Override
    public void openNewExerciseActivity() {
        startActivity(ExerciseActivity.intentForNew(this));
    }

    @Override
    public void openExistingExerciseActivity(@NonNull ExerciseId exerciseId) {
        startActivity(ExerciseActivity.intentForExisting(this, exerciseId));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError() {

    }
}
