package vaughandroid.vigor.domain.workout;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import vaughandroid.util.Assertions;
import vaughandroid.vigor.domain.exercise.Exercise;

/**
 * Data for a workout session.
 *
 * @author Chris
 */
// TODO: Implement equals(), hashCode() for testing purposes
public final class Workout implements Serializable {

  public static Builder builder() {
    return new Builder();
  }

  @NonNull private WorkoutId id;
  @NonNull private final ArrayList<Exercise> exercises;

  private Workout(@NonNull WorkoutId id, List<Exercise> exercises) {
    this.id = id;
    this.exercises = new ArrayList<>(exercises);
  }

  @NonNull public WorkoutId id() {
    return id;
  }

  public void setId(@NonNull WorkoutId id) {
    this.id = id;
  }

  /**
   * @return an unmodifiable view of the List of Exercises
   */
  @NonNull public List<Exercise> exercises() {
    return Collections.unmodifiableList(exercises);
  }

  public void addExercise(@NonNull Exercise exercise) {
    int idx = exercises.indexOf(exercise);
    if (idx == -1) {
      exercises.add(exercise);
    } else {
      // Replace an existing Exercise.
      exercises.set(idx, exercise);
    }
  }

  public boolean removeExercise(@NonNull Exercise exercise) {
    return exercises.remove(exercise);
  }

  public static class Builder {

    private WorkoutId id = WorkoutId.UNASSIGNED;
    private List<Exercise> exercises = new ArrayList<>();

    public Builder id(@NonNull WorkoutId id) {
      this.id = id;
      return this;
    }

    public Builder exercises(List<Exercise> exercises) {
      this.exercises.clear();
      this.exercises.addAll(exercises);
      return this;
    }

    public Workout build() {
      Assertions.checkNotNull(id);
      return new Workout(id, exercises);
    }
  }
}
