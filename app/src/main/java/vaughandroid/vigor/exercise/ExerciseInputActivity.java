/*
 * Copyright (c) 2016 Christopher John Vaughan. All rights reserved.
 */
package vaughandroid.vigor.exercise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vaughandroid.vigor.R;

/**
 * @author Chris
 */
public class ExerciseInputActivity extends AppCompatActivity {

    public static Intent createIntent(Context appContext) {
        return new Intent(appContext, ExerciseInputActivity.class);
    }

    @BindView(R.id.weight_and_reps_NumberInputView_weight) NumberInputView mWeightNumberInputView;
    @BindView(R.id.weight_and_reps_NumberInputView_reps) NumberInputView mRepsNumberInputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_input);
        ButterKnife.bind(this);
        initToolbar();
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
        // TODO: show confirmation dialog
        finish();
        return true;
    }

    @OnClick(R.id.weight_and_reps_button_add)
    void onClickAddButton(View view) {
        // TEMP
        String description =
                getString(R.string.exercise_descrption_weight_and_reps, mWeightNumberInputView, mRepsNumberInputView);
        Snackbar.make(view, description, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
