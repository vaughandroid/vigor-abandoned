package vaughandroid.vigor.data.exercise.type;

import android.support.annotation.NonNull;

import vaughandroid.vigor.domain.exercise.type.ExerciseType;

/**
 * Data Transfer Object for {@link ExerciseType}s
 *
 * @author Chris
 */
public class ExerciseTypeDto {

    @NonNull
    public String guid;
    @NonNull
    public String name;
}
