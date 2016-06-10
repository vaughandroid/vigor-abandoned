/*
 * Copyright (c) 2016 Christopher John Vaughan. All rights reserved.
 */
package vaughandroid.vigor.app.exercise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.math.BigDecimal;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vaughandroid.vigor.R;
import vaughandroid.vigor.app.VigorActivity;
import vaughandroid.vigor.app.widgets.NumberInputView;
import vaughandroid.vigor.domain.exercise.Exercise;

/**
 * @author Chris
 */
public class ExerciseActivity extends VigorActivity implements ExerciseContract.View {

    private static final String EXTRA_SAVED_EXERCISE = "savedExercise";

    public static IntentBuilder intentBuilder() {
        return new IntentBuilder();
    }

    @BindView(R.id.content_exercise_NumberInputView_weight) NumberInputView weightNumberInputView;
    @BindView(R.id.content_exercise_NumberInputView_reps) NumberInputView repsNumberInputView;

    @Inject ExerciseContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);

        setContentView(R.layout.activity_exercise);
        ButterKnife.bind(this);
        initToolbar();

        presenter.setView(this);
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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @OnClick(R.id.content_exercise_Button_confirm)
    void onClickConfirmButton(View view) {
        presenter.onValuesConfirmed();
    }

    @Override
    public void setWeight(@NonNull BigDecimal weight) {
        weightNumberInputView.setValue(weight);
    }

    @Override
    public void setWeightUnits(@NonNull String units) {
        weightNumberInputView.setUnits(units);
    }

    @Override
    public void setReps(int reps) {
        repsNumberInputView.setValue(reps);
    }

    @Override
    public void finish(Exercise exercise) {
        setResult(RESULT_OK, new Intent().putExtra(EXTRA_SAVED_EXERCISE, exercise));
    }

    public static class IntentBuilder {

        private final Bundle extras = new Bundle();

        private IntentBuilder() {}

        public IntentBuilder exercise(Exercise exercise) {
            extras.putSerializable(EXTRA_SAVED_EXERCISE, exercise);
            return this;
        }

        public Intent build(Context appContext) {
            return new Intent(appContext, ExerciseActivity.class).putExtras(extras);
        }
    }
}
