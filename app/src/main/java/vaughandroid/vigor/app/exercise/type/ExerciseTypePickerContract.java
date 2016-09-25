package vaughandroid.vigor.app.exercise.type;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

  interface Presenter {
    void init(@NonNull View view, @NonNull ExerciseId exerciseId);

    void onSearchTextUpdated(@NonNull String text);

    void onTypePicked(@NonNull ExerciseType exerciseType);

    void onError(Throwable t);
  }
}
