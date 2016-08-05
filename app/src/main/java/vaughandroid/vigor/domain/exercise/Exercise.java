package vaughandroid.vigor.domain.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.Serializable;
import java.math.BigDecimal;
import vaughandroid.util.Assertions;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;
import vaughandroid.vigor.domain.workout.WorkoutId;

/**
 * Data for an exercise.
 *
 * @author Chris
 */
public class Exercise implements Serializable {

  public static Builder builder() {
    return new Builder();
  }

  @NonNull private ExerciseId id;
  @NonNull private final WorkoutId workoutId;
  @NonNull private ExerciseType type;
  @Nullable private Integer reps;
  @Nullable private BigDecimal weight;

  private Exercise(@NonNull ExerciseId id, @NonNull WorkoutId workoutId,
      @NonNull ExerciseType type, @Nullable Integer reps, @Nullable BigDecimal weight) {
    this.id = id;
    this.workoutId = workoutId;
    this.type = type;
    this.reps = reps;
    this.weight = weight;
  }

  @NonNull public ExerciseId id() {
    return id;
  }

  public void setId(@NonNull ExerciseId id) {
    this.id = id;
  }

  @NonNull public WorkoutId workoutId() {
    return workoutId;
  }

  @NonNull public String workoutGuid() {
    return workoutId.guid();
  }

  @NonNull public ExerciseType type() {
    return type;
  }

  public void setType(@NonNull ExerciseType type) {
    this.type = type;
  }

  @Nullable public String typeGuid() {
    return type.guid();
  }

  @Nullable public Integer reps() {
    return reps;
  }

  public void setReps(@Nullable Integer reps) {
    this.reps = reps;
  }

  @Nullable public BigDecimal weight() {
    return weight;
  }

  public void setWeight(@Nullable BigDecimal weight) {
    this.weight = weight;
  }

  @Nullable public String weightAsString() {
    return weight != null ? weight.toPlainString() : null;
  }

  public static class Builder {
    private ExerciseId id = ExerciseId.UNASSIGNED;
    private WorkoutId workoutId;
    private ExerciseType type = ExerciseType.UNSET;
    private Integer reps;
    private BigDecimal weight;

    public Builder id(@NonNull ExerciseId id) {
      this.id = id;
      return this;
    }

    public Builder type(@NonNull ExerciseType type) {
      this.type = type;
      return this;
    }

    public Builder workoutId(@NonNull WorkoutId workoutId) {
      this.workoutId = workoutId;
      return this;
    }

    public Builder reps(@Nullable Integer reps) {
      this.reps = reps;
      return this;
    }

    public Builder weight(@Nullable BigDecimal weight) {
      this.weight = weight;
      return this;
    }

    public Exercise build() {
      Assertions.checkNotNull(id, "id not set");
      Assertions.checkNotNull(workoutId, "workoutId not set");
      Assertions.checkNotNull(type, "type not set");
      return new Exercise(id, workoutId, type, reps, weight);
    }
  }
}
