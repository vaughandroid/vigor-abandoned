package vaughandroid.vigor.domain.exercise;

import org.jetbrains.annotations.NotNull;

import rx.Observable;

/**
 * Interface for a class which can store & retrieve exercise data.
 *
 * @author chris.vaughan@laterooms.com
 */
public interface ExerciseRepository {

    @NotNull Observable<SavedExercise> addExercise(@NotNull Exercise exercise);
}
