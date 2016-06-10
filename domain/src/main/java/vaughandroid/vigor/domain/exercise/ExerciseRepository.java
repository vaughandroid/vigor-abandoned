package vaughandroid.vigor.domain.exercise;

import org.jetbrains.annotations.NotNull;

import rx.Observable;

/**
 * Interface for a class which can store & retrieve exercise data.
 *
 * @author Chris
 */
public interface ExerciseRepository {

    @NotNull Observable<SavedExercise> addExercise(@NotNull Exercise exercise);

    @NotNull Observable<SavedExercise> getExercise(@NotNull SavedExerciseId id);
}
