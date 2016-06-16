package vaughandroid.vigor.domain.workout;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

/**
 * TODO: javadoc
 *
 * @author Chris
 */
@AutoValue
public abstract class WorkoutId implements Serializable {

    public static WorkoutId create(String guid) {
        return new AutoValue_WorkoutId(guid);
    }

    public abstract String guid();
}
