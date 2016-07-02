package vaughandroid.vigor.app.exercise.type;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.EditText;

import com.google.common.collect.ImmutableList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;
import vaughandroid.vigor.R;
import vaughandroid.vigor.app.VigorActivity;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;

/**
 * Activity for picking an {@link ExerciseType}
 *
 * @author Chris
 */
public class ExerciseTypePickerActivity extends VigorActivity implements ExerciseTypePickerContract.View {

    private static final String EXTRA_TYPE = "exerciseType";

    public static Intent intent(@NonNull Context context, @NonNull ExerciseType currentType) {
        return new Intent(context, ExerciseTypePickerActivity.class)
                .putExtra(EXTRA_TYPE, currentType);
    }

    @NonNull
    public static ExerciseType getTypeFromResult(@NonNull Intent data) {
        if (!data.hasExtra(EXTRA_TYPE)) {
            throw new IllegalArgumentException("Invalid data");
        }
        return (ExerciseType) data.getSerializableExtra(EXTRA_TYPE);
    }

    @Inject ExerciseTypePickerContract.Presenter presenter;

    @BindView(R.id.activity_exercise_type_picker_EditText) EditText editText;
    @BindView(R.id.activity_exercise_type_picker_RecyclerView) RecyclerView exerciseTypeRecyclerView;
    private final ExerciseTypeAdapter exerciseTypeAdapter = new ExerciseTypeAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);

        initViews();
        initPresenter();
    }

    private void initViews() {
        setContentView(R.layout.activity_exercise_type_picker);
        ButterKnife.bind(this);

        exerciseTypeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        exerciseTypeRecyclerView.setAdapter(exerciseTypeAdapter);
        exerciseTypeRecyclerView.setHasFixedSize(true);
    }

    private void initPresenter() {
        presenter.setView(this);
    }

    @Override
    public void setSearchText(@NonNull String text) {
        editText.setText(text);
    }

    @OnTextChanged(R.id.activity_exercise_type_picker_EditText)
    public void onEditTextTextChanged(CharSequence newText) {
        presenter.onTextEntered(newText.toString());
    }

    @OnEditorAction(R.id.activity_exercise_type_picker_EditText)
    boolean onEditTextEditorAction(KeyEvent event) {
        boolean handled = false;
        if (event != null) {
            presenter.onTextConfirmed();
            handled = true;
        }
        return handled;
    }

    @Override
    public void setListEntries(@NonNull ImmutableList<ExerciseType> entries) {
        exerciseTypeAdapter.setExerciseTypes(entries);
    }

    @Override
    public void returnPickedType(@NonNull ExerciseType exerciseType) {
        setResult(RESULT_OK, new Intent().putExtra(EXTRA_TYPE, exerciseType));
    }
}
