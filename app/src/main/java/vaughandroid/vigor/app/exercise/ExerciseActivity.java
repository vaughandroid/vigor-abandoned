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
import android.view.View;

import java.math.BigDecimal;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vaughandroid.vigor.R;
import vaughandroid.vigor.app.VigorActivity;
import vaughandroid.vigor.app.widgets.NumberInputView;
import vaughandroid.vigor.domain.exercise.ExerciseId;

/**
 * @author Chris
 */
public class ExerciseActivity extends VigorActivity implements ExerciseContract.View {

    private static final String EXTRA_SAVED_EXERCISE_ID = "savedExerciseId";

    public static Intent intentForNew(@NonNull Context context) {
        return new Intent(context, ExerciseActivity.class);
    }

    public static Intent intentForExisting(@NonNull Context context, @NonNull ExerciseId exerciseId) {
        Bundle extras = new Bundle();
        extras.putSerializable(EXTRA_SAVED_EXERCISE_ID, exerciseId);
        return intentForNew(context).putExtras(extras);
    }

    @Inject ExerciseContract.Presenter presenter;

    @BindView(R.id.content_exercise_NumberInputView_weight) NumberInputView weightNumberInputView;
    @BindView(R.id.content_exercise_NumberInputView_reps) NumberInputView repsNumberInputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        weightNumberInputView.setValueChangedListener(new NumberInputView.ValueChangedListener() {
            @Override
            public void onValueChanged(BigDecimal newValue) {
                presenter.onWeightEntered(newValue);
            }
        });

        repsNumberInputView.setValueChangedListener(new NumberInputView.ValueChangedListener() {
            @Override
            public void onValueChanged(BigDecimal newValue) {
                presenter.onRepsEntered(newValue.intValue());
            }
        });
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
        presenter.setExerciseId(getExerciseId());
    }

    @Nullable
    private ExerciseId getExerciseId() {
        return getIntent().getExtras() != null ?
                (ExerciseId) getIntent().getExtras().getSerializable(EXTRA_SAVED_EXERCISE_ID) : null;
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
    public void setWeight(@Nullable BigDecimal weight) {
        weightNumberInputView.setValue(weight);
    }

    @Override
    public void setWeightUnits(@NonNull String units) {
        weightNumberInputView.setUnits(units);
    }

    @Override
    public void setReps(@Nullable Integer reps) {
        repsNumberInputView.setValue(reps);
    }

    @Override
    public void onSaved() {
        setResult(RESULT_OK);
        finish();
    }
}
