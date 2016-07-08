package vaughandroid.vigor.app.exercise.type;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;

/**
 * MVP contract for the {@link ExerciseType} picker.
 *
 * @author Chris
 */
public interface ExerciseTypePickerContract {

    interface View {
        Observable<String> searchText();

        Observable<ExerciseType> typePicked();

        void setSearchText(@NonNull String text);
        void setListEntries(@NonNull List<ExerciseType> entries);

        void returnPickedType(@NonNull ExerciseType exerciseType);
    }

    interface Presenter extends vaughandroid.vigor.app.mvp.Presenter {
        void setView(@NonNull View view);
        void init(@NonNull ExerciseType exerciseType);
    }
}
