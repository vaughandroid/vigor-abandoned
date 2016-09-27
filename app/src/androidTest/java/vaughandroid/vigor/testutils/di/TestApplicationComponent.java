package vaughandroid.vigor.testutils.di;

import dagger.Component;
import vaughandroid.vigor.app.di.ApplicationComponent;
import vaughandroid.vigor.app.di.ApplicationScope;
import vaughandroid.vigor.app.exercise.ExerciseActivityRobot;
import vaughandroid.vigor.app.rx.UtilsModule;
import vaughandroid.vigor.app.workout.WorkoutActivityRobot;

/**
 * Inject mocks, stubs & spies for testing.
 *
 * @author chris.vaughan@laterooms.com
 */
@Component(modules = {
    MockFirebaseModule.class, TestSchedulerModule.class, UtilsModule.class,
}) @ApplicationScope public interface TestApplicationComponent extends ApplicationComponent {

  void inject(WorkoutActivityRobot robot);

  void inject(ExerciseActivityRobot robot);
}
