package vaughandroid.vigor.domain.workout;

import com.google.auto.value.AutoValue;

/**
 * TODO: javadoc
 *
 * @author Chris
 */
@AutoValue
public abstract class WorkoutId {

    public static WorkoutId create(int id) {
        return new AutoValue_WorkoutId(id);
    }

    public abstract int id();
}
