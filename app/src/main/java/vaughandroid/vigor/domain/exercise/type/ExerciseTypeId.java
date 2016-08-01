package vaughandroid.vigor.domain.exercise.type;

import android.support.annotation.NonNull;
import com.google.auto.value.AutoValue;
import java.io.Serializable;

/**
 * Unique ID for {@link ExerciseType}s
 *
 * @author Chris
 */
@AutoValue public abstract class ExerciseTypeId implements Serializable {

  public static final ExerciseTypeId UNASSIGNED = create("unassigned");

  public static ExerciseTypeId create(String guid) {
    return new AutoValue_ExerciseTypeId(guid);
  }

  @NonNull public abstract String guid();
}
