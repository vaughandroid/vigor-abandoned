/**
 * (C) Copyright 2016 Christopher John Vaughan. All rights reserved.
 */
package vaughandroid.vigor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Chris
 */
public class WeightAndRepsActivity extends AppCompatActivity {

    @Bind(R.id.weight_and_reps_edittext_weight)
    EditText mWeightEditText;
    @Bind(R.id.weight_and_reps_edittext_reps)
    EditText mRepsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_and_reps);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

    @OnClick(R.id.weight_and_reps_button_add)
    void onClickAddButton(View view) {
        // TEMP
        String description = getString(R.string.exercise_descrption_weight_and_reps, mWeightEditText, mRepsEditText);
        Snackbar.make(view, description, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }

    public static Intent createIntent(Context appContext) {
        return new Intent(appContext, WeightAndRepsActivity.class);
    }
}
