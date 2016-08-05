package vaughandroid.vigor.app.exercise.type;

import android.support.annotation.NonNull;
import java.util.List;
import vaughandroid.vigor.app.mvp.LCEView;
import vaughandroid.vigor.domain.exercise.ExerciseId;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;

/**
 * MVP contract for the {@link ExerciseType} picker.
 *
 * @author Chris
 */
public interface ExerciseTypePickerContract {

  interface View extends LCEView {
    void setSearchText(@NonNull String text);

    void setExerciseTypes(@NonNull List<ExerciseType> entries);

    void finish();
  }

  interface Presenter extends vaughandroid.vigor.app.mvp.Presenter<View> {
    void init(@NonNull ExerciseId exerciseId);

    void onSearchTextUpdated(@NonNull String text);

    void onTypePicked(@NonNull ExerciseType exerciseType);

    void onErrorDialogDismissed();
  }
}
