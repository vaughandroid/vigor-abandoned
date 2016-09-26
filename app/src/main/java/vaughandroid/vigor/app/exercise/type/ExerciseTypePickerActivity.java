package vaughandroid.vigor.app.exercise.type;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.common.base.Preconditions;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.trello.rxlifecycle.ActivityEvent;
import java.util.List;
import javax.inject.Inject;
import vaughandroid.vigor.R;
import vaughandroid.vigor.app.VigorActivity;
import vaughandroid.vigor.app.dialogs.ErrorDialogFragment;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;

/**
 * Activity for picking an {@link ExerciseType}
 *
 * @author Chris
 */
public class ExerciseTypePickerActivity extends VigorActivity
    implements ExerciseTypePickerContract.View {

  private static final String EXTRA_EXERCISE_ID = "exerciseId";

  private final ExerciseTypeAdapter exerciseTypeAdapter = new ExerciseTypeAdapter();

  @Inject ExerciseTypePickerContract.Presenter presenter;

  @BindView(R.id.content_loading_root) View loadingRoot;
  @BindView(R.id.content_exercise_type_picker_root) View contentRoot;
  @BindView(R.id.content_error_root) View errorRoot;
  @BindView(R.id.content_exercise_type_picker_EditText) EditText editText;
  @BindView(R.id.content_exercise_type_picker_RecyclerView) RecyclerView exerciseTypeRecyclerView;

  public static Intent intent(@NonNull Context context, @NonNull ExerciseId exerciseId) {
    return new Intent(context, ExerciseTypePickerActivity.class).putExtra(EXTRA_EXERCISE_ID, exerciseId);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
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

    exerciseTypeAdapter.exerciseTypeClickedObservable()
        .compose(bindUntilEvent(ActivityEvent.DESTROY))
        .subscribe(exerciseType -> presenter.onTypePicked(exerciseType),
            t -> presenter.onError(t));

    RxTextView.textChanges(editText)
        .map(CharSequence::toString)
        .compose(bindUntilEvent(ActivityEvent.DESTROY))
        .subscribe(text -> presenter.onSearchTextUpdated(text), t -> presenter.onError(t));
  }

  private void initPresenter() {
    presenter.init(this, getExerciseId());
  }

  private ExerciseId getExerciseId() {
    Intent intent = getIntent();
    Preconditions.checkState(intent.hasExtra(EXTRA_EXERCISE_ID), "Missing extra: '%s'",
        EXTRA_EXERCISE_ID);
    return (ExerciseId) intent.getSerializableExtra(EXTRA_EXERCISE_ID);
  }

  @Override public void setSearchText(@NonNull String text) {
    editText.setText(text);
  }

  @Override public void setExerciseTypes(@NonNull List<ExerciseType> entries) {
    exerciseTypeAdapter.setExerciseTypes(entries);
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
