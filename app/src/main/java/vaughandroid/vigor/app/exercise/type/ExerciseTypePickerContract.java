package vaughandroid.vigor.app.exercise.type;

import android.support.annotation.NonNull;
import java.util.List;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;

/**
 * MVP contract for the {@link ExerciseType} picker.
 *
 * @author Chris
 */
public interface ExerciseTypePickerContract {

  interface View {
    void setSearchText(@NonNull String text);

    void setListEntries(@NonNull List<ExerciseType> entries);

    void showError();

    void returnPickedType(@NonNull ExerciseType exerciseType);

    void returnCancelled();
  }

  interface Presenter extends vaughandroid.vigor.app.mvp.Presenter<View> {
    void init(@NonNull ExerciseType exerciseType);

    void onSearchTextUpdated(@NonNull String text);

    void onTypePicked(@NonNull ExerciseType exerciseType);

    void onErrorDialogDismissed();
  }
}
