package vaughandroid.vigor.app.exercise.type;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
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

    public static Intent intent(@NonNull Context context, @NonNull ExerciseType exerciseType) {
        return new Intent(context, ExerciseTypePickerActivity.class)
                .putExtra(EXTRA_TYPE, exerciseType);
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
        presenter.init(getExerciseType());
    }

    private ExerciseType getExerciseType() {
        Intent intent = getIntent();
        com.google.common.base.Preconditions.checkState(intent.hasExtra(EXTRA_TYPE), "Missing extra: '%s'", EXTRA_TYPE);
        return (ExerciseType) intent.getSerializableExtra(EXTRA_TYPE);
    }

    @Override
    public Observable<String> searchText() {
        return RxTextView.textChanges(editText)
                .map(CharSequence::toString);
    }

    @Override
    public Observable<ExerciseType> typePicked() {
        return exerciseTypeAdapter.exerciseTypeClickedObservable();
    }

    @Override
    public void setSearchText(@NonNull String text) {
        editText.setText(text);
    }

    @Override
    public void setListEntries(@NonNull List<ExerciseType> entries) {
        exerciseTypeAdapter.setExerciseTypes(entries);
    }

    @Override
    public void returnPickedType(@NonNull ExerciseType exerciseType) {
        setResult(RESULT_OK, new Intent().putExtra(EXTRA_TYPE, exerciseType));
        finish();
    }
}
