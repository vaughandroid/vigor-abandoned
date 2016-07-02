package vaughandroid.vigor.domain.exercise;

import org.jetbrains.annotations.NotNull;

import rx.Observable;

/**
 * Interface for a class which can store & retrieve {@link Exercise} data.
 *
 * @author Chris
 */
public interface ExerciseRepository {

    @NotNull Observable<Exercise> addExercise(@NotNull Exercise exercise);
    @NotNull Observable<Exercise> getExercise(@NotNull ExerciseId id);
}
