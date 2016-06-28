package vaughandroid.vigor.app.exercise.type;

import dagger.Module;
import dagger.Provides;
import vaughandroid.vigor.app.di.ActivityScope;

/**
 * TODO: javadoc
 *
 * @author Chris
 */
@Module
public class ExerciseTypeModule {

    @Provides @ActivityScope public ExerciseTypePickerContract.Presenter provideExerciseTypePickerPresenter(
            ExerciseTypePickerPresenter presenter) {
        return presenter;
    }
}
