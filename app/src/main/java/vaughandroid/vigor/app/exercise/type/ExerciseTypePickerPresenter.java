package vaughandroid.vigor.app.exercise.type;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import vaughandroid.vigor.domain.exercise.type.ExerciseType;

/**
 * TODO: javadoc
 *
 * @author chris.vaughan@laterooms.com
 */
public class ExerciseTypePickerPresenter implements ExerciseTypePickerContract.Presenter {

    @Inject
    public ExerciseTypePickerPresenter() {}

    @Override
    public void setView(@Nullable ExerciseTypePickerContract.View view) {

    }

    @Override
    public void init(@NonNull ExerciseType exerciseType) {

    }

    @Override
    public void onTextEntered(@NonNull String text) {

    }

    @Override
    public void onTextConfirmed() {

    }

    @Override
    public void onTypePickedFromList(@NonNull ExerciseType type) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }
}
