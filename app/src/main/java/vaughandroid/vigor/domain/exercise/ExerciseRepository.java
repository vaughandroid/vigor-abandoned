package vaughandroid.vigor.domain.exercise;

import android.support.annotation.NonNull;
import rx.Observable;
import rx.Single;

/**
 * Interface for a class which can store & retrieve {@link Exercise} data.
 *
 * @author Chris
 */
public interface ExerciseRepository {

  @NonNull Single<Exercise> addExercise(@NonNull Exercise exercise);

  @NonNull Observable<Exercise> getExercise(
      @NonNull vaughandroid.vigor.domain.exercise.ExerciseId id);
}
