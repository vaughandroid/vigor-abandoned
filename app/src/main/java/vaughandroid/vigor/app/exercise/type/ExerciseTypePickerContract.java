package vaughandroid.vigor.app.exercise.type;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import vaughandroid.vigor.domain.exercise.type.ExerciseType;

/**
 * MVP contract for the {@link ExerciseType} picker.
 *
 * @author Chris
 */
public interface ExerciseTypePickerContract {

    interface View {
        void setSearchText(@NonNull String text);
        void setListEntries(@NonNull ImmutableList<ExerciseType> entries);

        void returnPickedType(@NonNull ExerciseType exerciseType);
    }

    interface Presenter extends vaughandroid.vigor.app.mvp.Presenter {
        void setView(@Nullable View view);
        void init(@NonNull ExerciseType exerciseType);

        void onTextEntered(@NonNull String text);
        void onTextConfirmed();
        void onTypePickedFromList(@NonNull ExerciseType type);
    }
}
