package vaughandroid.vigor.domain.workout;

import com.google.auto.value.AutoValue;
import java.io.Serializable;

/**
 * Unique ID for a {@link Workout}
 *
 * @author Chris
 */
@AutoValue public abstract class WorkoutId implements Serializable {

  public static final WorkoutId UNASSIGNED = create("unassigned");

  public static WorkoutId create(String guid) {
    return new AutoValue_WorkoutId(guid);
  }

  public abstract String guid();
}
