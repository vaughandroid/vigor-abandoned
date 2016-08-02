package vaughandroid.vigor.app.exercise.type;

import dagger.Module;
import dagger.Provides;
import vaughandroid.vigor.app.di.ActivityScope;
import vaughandroid.vigor.domain.exercise.type.ExerciseTypeRepository;

/**
 * @author Chris
 */
@Module public class ExerciseTypeModule {

  @Provides @ActivityScope
  public ExerciseTypePickerContract.Presenter provideExerciseTypePickerPresenter(
      ExerciseTypePickerPresenter presenter) {
    return presenter;
  }

  @Provides @ActivityScope public ExerciseTypeRepository provideExerciseTypeRepository(
      vaughandroid.vigor.data.exercise.type.ExerciseTypeRepository exerciseTypeRepository) {
    return exerciseTypeRepository;
  }
}
